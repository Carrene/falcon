package de.netalic.falcon.ui.transfer;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferPresenter implements TransferContract.Presenter {

    private TransferContract.View mTransferView;


    public TransferPresenter(TransferContract.View transferView) {
        mTransferView = checkNotNull(transferView);
        mTransferView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
