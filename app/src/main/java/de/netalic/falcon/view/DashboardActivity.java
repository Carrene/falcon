package de.netalic.falcon.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.DashboardPresenter;
import de.netalic.falcon.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private static final String ARGUMENT_USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        User user=new User();
        //User user = getIntent().getExtras().getParcelable(ARGUMENT_USER);

        Toolbar toolbar = findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {

            actionBar.setTitle(getString(R.string.dashboard_dashboard));

        }

        mDrawerLayout = findViewById(R.id.drawerlayout_dashboard);
        mDrawerLayout.setStatusBarBackground(R.color.greenHaze);



        ActionBarDrawerToggle aToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(aToggle);
        aToggle.syncState();


        NavigationView navigationView = findViewById(R.id.navigationview_dashboard);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_dashboard_fragmentcontainer);
        if (dashboardFragment == null) {
            dashboardFragment = DashboardFragment.newInstance(user);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), dashboardFragment, R.id.framelayout_dashboard_fragmentcontainer);
        }

        new DashboardPresenter(dashboardFragment);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(

                menuItem -> {
                    switch (menuItem.getItemId()) {
                        case R.id.item_dashboard_dashboardnavigation:

                            break;
                        case R.id.item_dashboard_chargenavigation:

                            break;
                        case R.id.item_dashboard_requesttoreceivenavigation:

                            break;
                        case R.id.item_dashboard_walletaddressnavigation:

                            break;
                        case R.id.item_dashboard_transactionhistorynavigation:

                            break;
                        case R.id.item_dashboard_chartnavigation:

                            break;
                        case R.id.item_dashboard_settingnavigation:

                            break;
                        case R.id.item_dashboard_helpnaigation:

                            break;

                        default:
                            break;
                    }
                    return true;
                });
    }

}
