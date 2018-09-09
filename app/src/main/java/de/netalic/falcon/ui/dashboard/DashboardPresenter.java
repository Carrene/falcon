package de.netalic.falcon.ui.dashboard;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.exchangeRate.ExchangeRateRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardPresenter implements DashboardContract.Presenter {


    @NonNull
    private final DashboardContract.View mDashboardView;

    public DashboardPresenter(@NonNull DashboardContract.View dashboardView) {

        mDashboardView = checkNotNull(dashboardView);
        mDashboardView.setPresenter(this);
    }

    @Override
    public void exchangeRate(Rate rate) {

        mDashboardView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(ExchangeRateRepository.class).get(rate.getCurrencyCode(), deal -> {

            if (deal.getThrowable() != null) {

                mDashboardView.dismissProgressBar();

            } else {


                switch (deal.getResponse().code()) {

                    case 200: {


                        mDashboardView.updateExchangeRateCurrency(deal.getModel());
                        break;
                    }
                    case 709: {

                        mDashboardView.showErrorInvalidCurrency();
                        break;
                    }
                    case 721: {

                        mDashboardView.showErrorRatesDoesNotExists();
                        break;
                    }

                }

            }
        });
        mDashboardView.dismissProgressBar();
    }

    @Override
    public void getWalletList() {

        mDashboardView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable()!=null){

            } else {
                switch (deal.getResponse().code()){

                    case 200:{

                        mDashboardView.setListWallet(deal.getResponse().body());
                        break;
                    }
                }

            }
        });
        mDashboardView.dismissProgressBar();
    }

    @Override
    public void start() {

    }
}