package de.netalic.falcon.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.TextView;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInResult;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.DashboardPresenter;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class DashboardActivity extends BaseActivity {

    private static final int DROP_IN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setPhoneNumber();
        setEmail();

        NavigationDrawerUtil.getDrawer(this, getToolbar(),1);

        DashboardFragment dashboardFragment = (DashboardFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_dashboard_fragmentcontainer);
        if (dashboardFragment == null) {
            dashboardFragment = DashboardFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), dashboardFragment, R.id.framelayout_dashboard_fragmentcontainer);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentMethodNonce = result.getPaymentMethodNonce().getNonce();
                // send paymentMethodNonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // canceled
            } else {
                // an error occurred, checked the returned exception
                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    public void setEmail() {

//        mTextViewEmail.setText(mUser.getEmail());

    }


    public void setPhoneNumber() {

//        mTextViewPhoneNumber.setText(mUser.getPhone());

    }
}