package de.netalic.falcon.ui.exchange;

public class ExchangeFailedPresenter implements ExchangeFailedContract.Presenter {

    private ExchangeFailedContract.View mExchangeFailedView;


    public ExchangeFailedPresenter(ExchangeFailedContract.View exchangeFailedView) {
        mExchangeFailedView = exchangeFailedView;
        mExchangeFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
