package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.model.ExchangeRate;
import de.netalic.falcon.repository.exchangeRate.ExchangeRateRepository;
import de.netalic.falcon.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardPresenter implements DashboardContract.Presenter {


    @NonNull
    private final DashboardContract.View mDashboardView;

    public DashboardPresenter(@NonNull DashboardContract.View dashboardView) {

        mDashboardView = checkNotNull(dashboardView);
        mDashboardView.setPresenter(this);
    }

    @Override
    public void exchangeRate(ExchangeRate exchangeRate) {

        mDashboardView.showProgressBar();

        ExchangeRateRepository.getInstance().get(exchangeRate.getCurrency().getCode(), deal -> {

            if (deal.getThrowable() != null) {

                mDashboardView.dismissProgressBar();

            } else {


                switch (deal.getResponse().code()) {

                    case 200: {


                        mDashboardView.updateExchangeRateCurrency(deal.getModel().getSell());
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
        WalletRepository.getInstance().getAll(deal -> {

            if (deal.getThrowable()!=null){
                mDashboardView.dismissProgressBar();

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