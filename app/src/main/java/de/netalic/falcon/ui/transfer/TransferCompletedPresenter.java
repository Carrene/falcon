package de.netalic.falcon.ui.transfer;

public class TransferCompletedPresenter implements TransferCompletedContract.Presenter {

    private TransferCompletedContract.View  mTransferCompletedView;

    public TransferCompletedPresenter(TransferCompletedContract.View transferCompletedView) {
        mTransferCompletedView = transferCompletedView;
        mTransferCompletedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
