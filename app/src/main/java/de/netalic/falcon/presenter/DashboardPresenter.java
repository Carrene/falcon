package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.repository.exchangeRate.ExchangeRateRepository;
import de.netalic.falcon.repository.user.UserRepository;

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

        ExchangeRateRepository.getInstance().exchangeRate(currency,deal -> {

            if (deal.getThrowable()!=null){

                mDashboardView.errorForNullCurrency();

            }
            else {

                switch (deal.getResponse().code()){

                    case 200:
                        mDashboardView.updateExchangeRateCurrency(deal.getModel().getSell());
                }

            }




        });

    }
}