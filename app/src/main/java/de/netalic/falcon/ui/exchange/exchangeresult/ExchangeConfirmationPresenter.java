package de.netalic.falcon.ui.exchange.exchangeresult;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExchangeConfirmationPresenter implements ExchangeConfirmationContract.Presenter {

    private ExchangeConfirmationContract.View mSendConfirmationView;

    public ExchangeConfirmationPresenter(ExchangeConfirmationContract.View transferConfirmationView) {
        mSendConfirmationView = checkNotNull(transferConfirmationView);
        mSendConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void finalizeTransfer(int transactionId) {

        mSendConfirmationView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).finalizeTransfer(transactionId, deal -> {

            if (deal.getThrowable() != null) {
                mSendConfirmationView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mSendConfirmationView.navigationToCompletedTransfer(deal.getResponse().body());
                        break;
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Transfer not found")) {
                            mSendConfirmationView.showErrorTransferNotFound404();
                        } else {

                            mSendConfirmationView.showErrorTryingToFinalizeSomeoneElseTransaction404();
                        }
                        break;
                    }

                    case 600: {

                        mSendConfirmationView.showError600();
                        break;
                    }

                    case 401: {

                        mSendConfirmationView.showError401();
                        break;
                    }

                    case 604: {

                        if (deal.getResponse().message().equals("Cannot Finalize Succeed Transaction")) {

                            mSendConfirmationView.shoeErrorFinalizingTransactionWithStatusOfSucceed604();
                        } else {

                            mSendConfirmationView.shoeErrorFinalizingTransactionWithStatusOfFailed604();
                        }

                        break;
                    }
                }


            }
            mSendConfirmationView.dismissProgressBar();
        });

    }
}
