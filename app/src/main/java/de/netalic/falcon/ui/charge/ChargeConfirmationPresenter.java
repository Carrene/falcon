package de.netalic.falcon.ui.charge;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeConfirmationPresenter implements ChargeConfirmationContract.Presenter {

    private final ChargeConfirmationContract.View mChargeConfirmationView;

    public ChargeConfirmationPresenter(ChargeConfirmationContract.View chargeConfirmationView) {

        mChargeConfirmationView = checkNotNull(chargeConfirmationView);
        mChargeConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void finalizeCharge(int transactionId, String braintreeNonce) {

        mChargeConfirmationView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).finalizeCharge(transactionId, braintreeNonce, deal -> {


            if (deal.getThrowable() != null) {

                mChargeConfirmationView.dismissProgressBar();


            } else {


                switch (deal.getResponse().code()) {


                    case 200: {

                        mChargeConfirmationView.navigationToChargeCompleted(deal.getModel());
                        break;
                    }

                    case 800: {

                        mChargeConfirmationView.navigationToChargeFailed(deal.getModel());
                        break;

                    }
                    case 404: {

                        if (deal.getResponse().message().equals("Wallet not found")) {

                            mChargeConfirmationView.showErrorWalletNotFound();
                        } else {

                            mChargeConfirmationView.showErrorDepositNotFound();

                        }
                        break;

                    }

                    case 700: {

                        mChargeConfirmationView.showErrorInvalidWalletId();
                        break;
                    }

                    case 722: {

                        mChargeConfirmationView.showErrorInvalidBraintreeNonce();
                        break;

                    }

                    case 723: {

                        mChargeConfirmationView.showErrorInvalidDepositId();
                        break;

                    }

                    case 727: {

                        mChargeConfirmationView.showErrorDepositAlreadySucceed();
                        break;

                    }

                }
            }


        });
    }
}
