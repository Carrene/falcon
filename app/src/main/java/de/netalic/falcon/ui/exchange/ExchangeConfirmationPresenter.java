package de.netalic.falcon.ui.exchange;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExchangeConfirmationPresenter implements ExchangeConfirmationContract.Presenter {

    private ExchangeConfirmationContract.View mExchangeConfirmationView;

    public ExchangeConfirmationPresenter(ExchangeConfirmationContract.View exchangeConfirmationView) {
        mExchangeConfirmationView = checkNotNull(exchangeConfirmationView);
        mExchangeConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void finalizeTransfer(int transactionId) {

        mExchangeConfirmationView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).finalizeTransfer(transactionId, deal -> {

            if (deal.getThrowable() != null) {
                mExchangeConfirmationView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mExchangeConfirmationView.navigationToCompletedTransfer(deal.getResponse().body());
                        break;
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Transfer not found")) {
                            mExchangeConfirmationView.showErrorTransferNotFound404();
                        } else {

                            mExchangeConfirmationView.showErrorTryingToFinalizeSomeoneElseTransaction404();
                        }
                        break;
                    }

                    case 600: {

                        mExchangeConfirmationView.showError600();
                        break;
                    }

                    case 401: {

                        mExchangeConfirmationView.showError401();
                        break;
                    }

                    case 604: {

                        if (deal.getResponse().message().equals("Cannot Finalize Succeed Transaction")) {

                            mExchangeConfirmationView.shoeErrorFinalizingTransactionWithStatusOfSucceed604();
                        } else {

                            mExchangeConfirmationView.shoeErrorFinalizingTransactionWithStatusOfFailed604();
                        }

                        break;
                    }
                }


            }
            mExchangeConfirmationView.dismissProgressBar();
        });

    }
}
