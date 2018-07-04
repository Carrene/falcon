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
    public void start() {

    }

    @Override
    public void exchangeRate(Currency currency) {

        mDashboardView.showProgressBar();

        ExchangeRateRepository.getInstance().exchangeRate(currency, deal -> {

            if (deal.getThrowable() != null) {

                mDashboardView.disMissShowProgressBar();
                mDashboardView.errorForNullCurrency();

            } else {

                switch (deal.getResponse().code()) {

                    case 200:
                        mDashboardView.disMissShowProgressBar();
                        mDashboardView.updateExchangeRateCurrency(deal.getModel().getSell());
                }

            }

        });

    }
}