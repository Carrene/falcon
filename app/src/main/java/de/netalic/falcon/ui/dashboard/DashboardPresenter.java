package de.netalic.falcon.ui.dashboard;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.wallet.WalletRepository;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardPresenter implements DashboardContract.Presenter {


    private List<Wallet> mData;

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
                mDashboardView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {
                    case 200: {
                        mDashboardView.setWalletList(deal.getResponse().body());
                        break;
                    }
                }
                mDashboardView.dismissProgressBar();
            }

        });

        mDashboardView.dismissProgressBar();
    }

    @Override
    public void start() {

    }
}