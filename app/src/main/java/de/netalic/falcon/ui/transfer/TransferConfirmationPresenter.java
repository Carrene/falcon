package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.transfer.TransferRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferConfirmationPresenter implements TransferConfirmationContract.Presenter {

    private TransferConfirmationContract.View mTransferConfirmationView;

    public TransferConfirmationPresenter(TransferConfirmationContract.View transferConfirmationView) {
        mTransferConfirmationView = checkNotNull(transferConfirmationView);
        mTransferConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void finalizeTransfer(int transactionId) {

        mTransferConfirmationView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransferRepository.class).get(transactionId, deal -> {

            if (deal.getThrowable() != null) {


            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mTransferConfirmationView.navigationToCompletedTransfer(deal.getResponse().body());
                        break;
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Transfer not found")) {
                            mTransferConfirmationView.showErrorTransferNotFound404();
                        } else {

                            mTransferConfirmationView.showErrorTryingToFinalizeSomeoneElseTransaction404();
                        }
                        break;
                    }

                    case 600: {

                        mTransferConfirmationView.showError600();
                        break;
                    }

                    case 401: {

                        mTransferConfirmationView.showError401();
                        break;
                    }

                    case 604: {

                        if (deal.getResponse().message().equals("Cannot Finalize Succeed Transaction")) {

                            mTransferConfirmationView.shoeErrorFinalizingTransactionWithStatusOfSucceed604();
                        } else {

                            mTransferConfirmationView.shoeErrorFinalizingTransactionWithStatusOfFailed604();
                        }

                        break;
                    }
                }

                mTransferConfirmationView.dismissProgressBar();
            }

        });

    }
}
