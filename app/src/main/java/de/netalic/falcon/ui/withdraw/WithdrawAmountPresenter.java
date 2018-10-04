package de.netalic.falcon.ui.withdraw;

import com.google.common.base.Joiner;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.user.UserRepository;
import nuesoft.helpdroid.crypto.CryptoUtil;
import nuesoft.helpdroid.crypto.HmacType;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawAmountPresenter implements WithdrawAmountContract.Presenter {

    private WithdrawAmountContract.View mViewWithdrawAmount;


    public WithdrawAmountPresenter(WithdrawAmountContract.View viewWithdrawAmount) {

        mViewWithdrawAmount = checkNotNull(viewWithdrawAmount);
        mViewWithdrawAmount.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void listExchangeRate() {

        mViewWithdrawAmount.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {


                mViewWithdrawAmount.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mViewWithdrawAmount.setRateList(deal.getResponse().body());
                    }

                }
            }
            mViewWithdrawAmount.dismissProgressBar();

        });
    }

    @Override
    public void generateQrCode(Map<String, Object> map) {

        String urlEncoded = Joiner.on("&").withKeyValueSeparator("=")
                .join(map);

        RepositoryLocator.getInstance().getRepository(UserRepository.class).get(null, deal -> {

            if (deal.getThrowable() == null) {
                try {

                    byte[] secret = android.util.Base64.decode(deal.getModel().getSecret(), android.util.Base64.DEFAULT);
                    byte[] hmacSecret = android.util.Base64.decode(deal.getModel().getHmacSecret(), android.util.Base64.DEFAULT);

                    byte[] iv = CryptoUtil.getSecureRandom(16);
                    byte[] plainText = urlEncoded.getBytes("UTF-8");

                    byte[] cipherText = CryptoUtil.encryptAesCbcPkcs5Padding(secret, iv, plainText);
                    byte[] hamc = CryptoUtil.hmac(HmacType.HmacSHA256, hmacSecret, cipherText);

                    String qrCodeContent = android.util.Base64.encodeToString(cipherText, android.util.Base64.DEFAULT) + "." + android.util.Base64.encodeToString(hamc, android.util.Base64.DEFAULT);
                    mViewWithdrawAmount.setQrCode(qrCodeContent);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();

                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}