package de.netalic.falcon.ui.exchange;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;

public class ExchangePresenter implements ExchangeContract.Presenter {

    private ExchangeContract.View mExchangeView;

    public ExchangePresenter(ExchangeFragment exchangeFragment) {
        mExchangeView = exchangeFragment;
        mExchangeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void listExchangeRate() {

        mExchangeView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {


                mExchangeView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mExchangeView.setRateList(deal.getResponse().body());
                        break;
                    }

                }
            }
            mExchangeView.dismissProgressBar();

        });
    }

    @Override
    public void transfer() {

    }

}
