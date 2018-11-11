package de.netalic.falcon.ui.dashboard;

import android.os.Bundle;

import com.mikepenz.materialdrawer.Drawer;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;


public class DashboardActivity extends BaseActivity {

//    private static final int DROP_IN_REQUEST = 1;
    public Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawer = NavigationDrawerUtil.getDrawer(this, getToolbar(), 1);

        DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().
                findFragmentById(R.id.framelayout_dashboard_fragmentcontainer);

        if (dashboardFragment == null) {
            dashboardFragment = DashboardFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), dashboardFragment,
                    R.id.framelayout_dashboard_fragmentcontainer);
        }
        new DashboardPresenter(dashboardFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dashboard;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.navigation_dashboard);
    }

}