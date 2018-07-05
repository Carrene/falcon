package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.repository.exchangeRate.ExchangeRateRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardPresenter implements DashboardContract.Presenter {


    @NonNull
    private final DashboardContract.View mDashboardView;

    public DashboardPresenter(@NonNull DashboardContract.View dashboardView) {

        mDashboardView = checkNotNull(dashboardView);
        mDashboardView.setPresenter(this);
    }

    @Override
    public void exchangeRate(Currency currency) {

        mDashboardView.showProgressBar();

        ExchangeRateRepository.getInstance().exchangeRate(currency, deal -> {

            if (deal.getThrowable() != null) {

                mDashboardView.dismissShowProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mDashboardView.dismissShowProgressBar();
                        mDashboardView.updateExchangeRateCurrency(deal.getModel().getSell());
                        break;
                    }
                    case 709: {
                        mDashboardView.dismissShowProgressBar();
                        mDashboardView.showErrorInvalidCurrency();
                        break;
                    }
                    case 721: {
                        mDashboardView.dismissShowProgressBar();
                        mDashboardView.showErrorRatesDoesNotExists();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void start() {



    }
}