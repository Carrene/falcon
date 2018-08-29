package de.netalic.falcon.ui.transfer;

public class TransferFailedPresenter implements TransferFailedContract.Presenter {

    private TransferFailedContract.View mTransferFailedView;


    public TransferFailedPresenter(TransferFailedContract.View transferFailedView) {
        mTransferFailedView = transferFailedView;
        mTransferFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
