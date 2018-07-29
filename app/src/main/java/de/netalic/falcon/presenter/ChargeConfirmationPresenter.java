package de.netalic.falcon.presenter;

import de.netalic.falcon.repository.wallet.WalletRepository;
import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeConfirmationPresenter implements ChargeConfirmationContract.Presenter {

    private final ChargeConfirmationContract.View mChargeConfirmationView;

    public ChargeConfirmationPresenter(ChargeConfirmationContract.View chargeConfirmationView) {
        mChargeConfirmationView=checkNotNull(chargeConfirmationView);
        mChargeConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void finalize(int walletId, int depositId, String braintreeNonce) {

        WalletRepository.getInstance().finalize(walletId,depositId,braintreeNonce,deal -> {

            mChargeConfirmationView.showProgressBar();

            if (deal.getThrowable()!=null){


            }else {


                switch (deal.getResponse().code()){

                    case 200:{

                        mChargeConfirmationView.navigationToChargeCompleted();
                        break;
                    }

                    case 800:{

                        mChargeConfirmationView.navigationToChargeFailed();
                        break;

                    }

                }
            }


        });
    }
}
