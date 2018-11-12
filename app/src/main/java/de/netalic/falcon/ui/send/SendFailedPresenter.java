package de.netalic.falcon.ui.send;

import de.netalic.falcon.ui.transfer.TransferFailedContract;

public class SendFailedPresenter implements SendFailedContract.Presenter {

    private SendFailedContract.View mSendFailedView;


    public SendFailedPresenter(SendFailedContract.View sendFailedView) {
        mSendFailedView = sendFailedView;
        mSendFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
