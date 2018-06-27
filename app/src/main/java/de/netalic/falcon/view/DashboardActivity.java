package de.netalic.falcon.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.DashboardPresenter;
import de.netalic.falcon.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        User user = getIntent().getExtras().getParcelable("User");

        Toolbar toolbar=findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(getString(R.string.dashboard_dashboard));
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
}
