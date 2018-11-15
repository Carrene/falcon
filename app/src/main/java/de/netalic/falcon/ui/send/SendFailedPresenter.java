package de.netalic.falcon.ui.send;

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
