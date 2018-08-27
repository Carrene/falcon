package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.ui.transfer.TransferPayeeContract.Presenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferPayeePresenter implements Presenter {

    private TransferPayeeContract.View mTransferPayeeView;

    public TransferPayeePresenter(TransferPayeeContract.View transferPayeeView) {
        mTransferPayeeView = checkNotNull(transferPayeeView);
        mTransferPayeeView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
