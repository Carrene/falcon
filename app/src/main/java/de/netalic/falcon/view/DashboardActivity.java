package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.DashboardPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_dashboard_fragmentcontainer);
        if (dashboardFragment == null) {
            dashboardFragment = DashboardFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), dashboardFragment, R.id.framelayout_dashboard_fragmentcontainer);
        }

        new DashboardPresenter(dashboardFragment);

    }
}
