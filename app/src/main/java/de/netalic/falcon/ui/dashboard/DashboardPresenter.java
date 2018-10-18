package de.netalic.falcon.ui.dashboard;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;
import de.netalic.falcon.util.ScreenLocker;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardPresenter implements DashboardContract.Presenter {


    @NonNull
    private final DashboardContract.View mDashboardView;
    SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
    Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());

    public DashboardPresenter(@NonNull DashboardContract.View dashboardView) {

        mDashboardView = checkNotNull(dashboardView);
        mDashboardView.setPresenter(this);
    }

    @Override
    public void getWalletList() {

        mDashboardView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {

            } else {
                switch (deal.getResponse().code()) {

                    case 200: {

                        mDashboardView.setListWallet(deal.getResponse().body());
                        break;
                    }
                }

            }

        });

        mDashboardView.dismissProgressBar();
    }

    @Override
    public void getListRates() {

        mDashboardView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {


            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mDashboardView.setListRates(deal.getResponse().body());

                        break;
                    }
                }

            }
            mDashboardView.dismissProgressBar();
        });

    }

    @Override
    public void baseCurrency() {

        mDashboardView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(UserRepository.class).get((int) tokenBody.get("id"), deal -> {

            if (deal.getThrowable() == null) {

                mDashboardView.setBaseCurrency(deal.getModel().getBaseCurrencyCode());

            } else {

                mDashboardView.setBaseCurrencyNotSet();
            }
        });
        mDashboardView.dismissProgressBar();
    }

    @Override
    public void start() {

        ScreenLocker.getInstance().start(new ScreenLocker.LockScreenInterface() {
            @Override
            public void lock() {

                Log.d("Timer", "Stopped in presenter");
            }
        });
    }
}