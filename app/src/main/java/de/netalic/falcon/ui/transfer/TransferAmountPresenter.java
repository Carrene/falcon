package de.netalic.falcon.ui.transfer;

import static com.google.common.base.Preconditions.checkNotNull;
public class TransferAmountPresenter implements TransferAmountContract.Presenter {

    private TransferAmountContract.View mTransferAmountView;


    public TransferAmountPresenter(TransferAmountContract.View transferAmountView) {
        mTransferAmountView =checkNotNull(transferAmountView);
        mTransferAmountView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
