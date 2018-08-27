package de.netalic.falcon.ui.transfer;

import static com.google.common.base.Preconditions.checkNotNull;
public class TransferConfirmationPresenter implements TransferConfirmationContract.Presenter {

    private TransferConfirmationContract.View mTransferConfirmationView;

    public TransferConfirmationPresenter(TransferConfirmationContract.View transferConfirmationView) {
        mTransferConfirmationView =checkNotNull(transferConfirmationView);
        mTransferConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
