package de.netalic.falcon.ui.send;

public class SendCompletedPresenter implements SendCompletedContract.Presenter {

    private SendCompletedContract.View  mSendCompletedView;

    public SendCompletedPresenter(SendCompletedContract.View sendCompletedView) {
        mSendCompletedView = sendCompletedView;
        mSendCompletedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
