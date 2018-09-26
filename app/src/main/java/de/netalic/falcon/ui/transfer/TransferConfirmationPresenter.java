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
                }

                mTransferConfirmationView.dismissProgressBar();
            }

        });

    }
}
