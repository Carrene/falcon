package de.netalic.falcon.ui.exchange.exchangeresult;

public class ExchangeCompletedPresenter implements ExchangeCompletedContract.Presenter {

    private ExchangeCompletedContract.View  mExchangeCompletedView;

    public ExchangeCompletedPresenter(ExchangeCompletedContract.View exchangeCompletedView) {
        mExchangeCompletedView = exchangeCompletedView;
        mExchangeCompletedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}

