package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardPresenter implements DashboardPresenterContract.Presenter {

    @NonNull
    private final DashboardPresenterContract.View mDashboardView;

    public DashboardPresenter(@NonNull DashboardPresenterContract.View dashboardView) {

        mDashboardView = checkNotNull(dashboardView);
        mDashboardView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}