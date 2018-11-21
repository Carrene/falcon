package de.netalic.falcon.ui.exchange;

public class ExchangeFailedPresenter implements ExchangeFailedContract.Presenter {

    private ExchangeFailedContract.View mSendFailedView;


    public ExchangeFailedPresenter(ExchangeFailedContract.View sendFailedView) {
        mSendFailedView = sendFailedView;
        mSendFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
