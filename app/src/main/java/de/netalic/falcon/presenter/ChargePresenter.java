package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;
import de.netalic.falcon.repository.wallet.WalletRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargePresenter implements ChargeContract.Presenter {

    @NonNull
    private final ChargeContract.View mChargeView;


    public ChargePresenter(ChargeContract.View chargeView) {


        mChargeView = checkNotNull(chargeView);
        mChargeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {

        mChargeView.showProgressBar();
        WalletRepository.getInstance().getAll(deal -> {

            if (deal.getThrowable() != null) {
                mChargeView.dismissProgressBar();

            } else {
                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeView.setListWallet(deal.getResponse().body());
                        break;
                    }
                }

            }
            mChargeView.dismissProgressBar();
        });

    }

    @Override
    public void charge(int id, double amount) {

        mChargeView.showProgressBar();
        WalletRepository.getInstance().charge(id, amount, deal -> {

            if (deal.getThrowable() != null) {

                mChargeView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeView.setToken(deal.getResponse().body());
                        break;
                    }

                    case 700: {

                        mChargeView.showErrorInvalidWalletId();
                        break;
                    }

                    case 702: {

                        mChargeView.showErrorInvalidAmount();
                        break;
                    }

                    case 703: {

                        mChargeView.showErrorAmountIsSmallerThanLowerBound();
                        break;
                    }

                    case 704: {

                        mChargeView.showErrorAmountIsGreaterThanUpperBound();
                        break;
                    }
                }
                mChargeView.dismissProgressBar();
            }

        });
    }

    @Override
    public void finalize(double amount, String braintreeNonce, String chargeDataToken) {

        WalletRepository.getInstance().finalize(amount, braintreeNonce, chargeDataToken, deal -> {

            System.out.println(deal.getResponse().code());
        });
    }
}
