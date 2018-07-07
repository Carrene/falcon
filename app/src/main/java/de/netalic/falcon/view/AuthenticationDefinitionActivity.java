package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.AuthenticationDefinitionContract;
import de.netalic.falcon.util.ActivityUtil;
import info.hoang8f.android.segmented.SegmentedGroup;


public class AuthenticationDefinitionActivity extends AppCompatActivity implements AuthenticationDefinitionContract.View, AuthenticationDefinitionPasswordFragment.NavigateToDashboardCallback
        , AuthenticationDefinitionPatternFragment.NavigateToDashboardCallback {

    private ActionBar mActionBar;
    private SegmentedGroup mSegmentedGroup;
    private User mUser;
    private AuthenticationDefinitionPasswordFragment mAuthenticationDefinitionPasswordFragment;
    private AuthenticationDefinitionPatternFragment mAuthenticationDefinitionPatternFragment;
    private static final String ARGUMENT_USER = "USER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticationdefinition);

        initUiComponents();
        initListeners();

        mUser = new User();
        //mUser = getIntent().getExtras().getParcelable(ARGUMENT_USER);


        Toolbar toolbar = findViewById(R.id.toolbar_authenticationdefinition);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setTitle(getString(R.string.authenticationdefinition_toolbartitle));
        }

        if (mAuthenticationDefinitionPasswordFragment == null) {
            mAuthenticationDefinitionPasswordFragment = new AuthenticationDefinitionPasswordFragment();
        }

        if (mAuthenticationDefinitionPatternFragment == null) {
            mAuthenticationDefinitionPatternFragment = new AuthenticationDefinitionPatternFragment();
        }

        changeAuthenticationDefinitionFragment(mAuthenticationDefinitionPasswordFragment);
    }

    private void initUiComponents() {

        mSegmentedGroup = findViewById(R.id.segmented_authenticationdefinition_segmentedgroup);
    }

    public void initListeners() {

        mSegmentedGroup.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.radiobutton_authenticationdefinition_password:

                    changeAuthenticationDefinitionFragment(mAuthenticationDefinitionPasswordFragment);
                    break;

                case R.id.radiobutton_authenticationdefinition_pattern:

                    changeAuthenticationDefinitionFragment(mAuthenticationDefinitionPatternFragment);
                    break;
            }
        });
    }

    public void changeAuthenticationDefinitionFragment(Fragment fragment) {

        ActivityUtil.replaceFragmentWithFragment(getSupportFragmentManager(), fragment, R.id.framelayout_authenticationdefinition_fragmentcontainer);
    }

    @Override
    public void setPresenter(AuthenticationDefinitionContract.Presenter presenter) {

    }

    @Override
    public void navigationToDashboardFromPassword(String credentialValue) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(ARGUMENT_USER, mUser);
        startActivity(intent);
    }

    @Override
    public void navigationToDashboardFromPattern(String credentialValue) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(ARGUMENT_USER, mUser);
        startActivity(intent);
    }
}