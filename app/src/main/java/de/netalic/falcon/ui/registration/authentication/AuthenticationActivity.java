package de.netalic.falcon.ui.registration.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.google.android.material.textfield.TextInputLayout;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;

public class AuthenticationActivity extends BaseActivity implements AuthenticationPatternFragment.NavigateToDashboardCallback,
        AuthenticationPasswordFragment.NavigateToDashboardCallback {

    private AuthenticationPresenter mAuthenticationPresenter;
    private AuthenticationPasswordFragment mAuthenticationPasswordFragment;
    private AuthenticationPatternFragment mAuthenticationPatternFragment;
    private FrameLayout mFrameLayout;
    private TextInputLayout mTextInputLayoutpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mFrameLayout = findViewById(R.id.framelayout_authentication_fragmentcontainer);

        if (mAuthenticationPasswordFragment == null) {

            mAuthenticationPasswordFragment = new AuthenticationPasswordFragment();
        }

        if (mAuthenticationPatternFragment == null) {

            mAuthenticationPatternFragment = new AuthenticationPatternFragment();
        }

        mAuthenticationPresenter = new AuthenticationPresenter(this);

        getTypeOfAuthentication();

    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_authentication;
    }

    private void getTypeOfAuthentication() {

        mAuthenticationPresenter.start();

    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.authentication_toolbar);
    }

    public void setPresenter(AuthenticationPresenter presenter) {

        mAuthenticationPresenter = presenter;

    }

    public void showPasswordLayout() {

        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), mAuthenticationPasswordFragment, R.id.framelayout_authentication_fragmentcontainer);
    }

    public void showPatternLayout() {

        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), mAuthenticationPatternFragment, R.id.framelayout_authentication_fragmentcontainer);
    }

    @Override
    public void checkCredentialValue(String credentialValue) {

        mAuthenticationPresenter.checkCredentialValue(credentialValue);
    }

    public void navigationToDashboard() {

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void showTextInputLayoutError() {

        mAuthenticationPasswordFragment.setErrorOnTextInputLayout();
    }

    public void showPatternLockError() {

        mAuthenticationPatternFragment.setErrorOnSnackBar();

    }

}
