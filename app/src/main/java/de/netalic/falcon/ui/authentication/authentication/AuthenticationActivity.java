package de.netalic.falcon.ui.authentication.authentication;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class AuthenticationActivity extends BaseActivity implements AuthenticationPatternFragment.NavigateToDashboardCallback,
        AuthenticationPasswordFragment.NavigateToDashboardCallback {

    private AuthenticationPresenter mAuthenticationPresenter;
    private AuthenticationPasswordFragment mAuthenticationPasswordFragment;
    private AuthenticationPatternFragment mAuthenticationPatternFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getTypeOfAuthentication();
        if (mAuthenticationPasswordFragment == null) {

            mAuthenticationPasswordFragment = new AuthenticationPasswordFragment();

        }

        if (mAuthenticationPatternFragment == null) {

            mAuthenticationPatternFragment = new AuthenticationPatternFragment();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentication;
    }

    private String getTypeOfAuthentication() {

        mAuthenticationPresenter.start();

        return null;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.authentication_toolbar);
    }

    @Override
    public void navigationToDashboard(int authenticationType) {

        if (authenticationType == 0) {

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), mAuthenticationPatternFragment, R.id.framelayout_authentication_fragmentcontainer);
        }

        if (authenticationType == 1) {

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), mAuthenticationPasswordFragment, R.id.framelayout_authentication_fragmentcontainer);
        }

    }

}
