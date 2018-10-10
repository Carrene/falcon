package de.netalic.falcon.ui.setting.authenticationdefinition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.registration.authentication.AuthenticationActivity;
import de.netalic.falcon.ui.registration.authnticationdefinition.AuthenticationDefinitionPasswordFragment;
import de.netalic.falcon.ui.registration.authnticationdefinition.AuthenticationDefinitionPatternFragment;
import de.netalic.falcon.util.ActivityUtil;
import info.hoang8f.android.segmented.SegmentedGroup;

public class SettingAuthenticationDefinitionActivity extends BaseActivity implements SettingAuthenticationDefinitionContract.View, AuthenticationDefinitionPasswordFragment.SaveCredentialCallback
        , AuthenticationDefinitionPatternFragment.SaveCredentialCallback {

    private SettingAuthenticationDefinitionContract.Presenter mPresenter;
    private SegmentedGroup mSegmentedGroup;
    private AuthenticationDefinitionPasswordFragment mAuthenticationDefinitionPasswordFragment;
    private AuthenticationDefinitionPatternFragment mAuthenticationDefinitionPatternFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setupBackButton();
        initUiComponents();
        initListeners();

        if (mAuthenticationDefinitionPasswordFragment == null) {
            mAuthenticationDefinitionPasswordFragment = new AuthenticationDefinitionPasswordFragment();
        }

        if (mAuthenticationDefinitionPatternFragment == null) {
            mAuthenticationDefinitionPatternFragment = new AuthenticationDefinitionPatternFragment();
        }

        changeAuthenticationDefinitionFragment(mAuthenticationDefinitionPatternFragment);
        new SettingAuthenticationDefinitionPresenter(this);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_authenticationdefinition;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.authenticationdefinition_toolbartitle);
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
    public void setPresenter(SettingAuthenticationDefinitionContract.Presenter presenter) {

        mPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    @Override
    public void saveCredentialFromPassword(String credentialValue) {

        mPresenter.changeCredential(credentialValue, Authentication.PASSWORD_TYPE);
    }

    @Override
    public void saveCredentialFromPattern(String credentialValue) {

        mPresenter.changeCredential(credentialValue, Authentication.PATTERN_TYPE);


    }

    @Override
    public void navigationToAuthentication() {

        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}