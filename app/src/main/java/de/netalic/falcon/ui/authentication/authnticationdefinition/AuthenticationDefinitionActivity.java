package de.netalic.falcon.ui.authentication.authnticationdefinition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;
import info.hoang8f.android.segmented.SegmentedGroup;


public class AuthenticationDefinitionActivity extends BaseActivity implements AuthenticationDefinitionContract.View, AuthenticationDefinitionPasswordFragment.NavigateToDashboardCallback
        , AuthenticationDefinitionPatternFragment.NavigateToDashboardCallback {

    private SegmentedGroup mSegmentedGroup;
    private AuthenticationDefinitionPasswordFragment mAuthenticationDefinitionPasswordFragment;
    private AuthenticationDefinitionPatternFragment mAuthenticationDefinitionPatternFragment;
    public static final int PASSWORD_TYPE=0;
    public static final int PATTERN_TYPE=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initUiComponents();
        initListeners();

        if (mAuthenticationDefinitionPasswordFragment == null) {
            mAuthenticationDefinitionPasswordFragment = new AuthenticationDefinitionPasswordFragment();
        }

        if (mAuthenticationDefinitionPatternFragment == null) {
            mAuthenticationDefinitionPatternFragment = new AuthenticationDefinitionPatternFragment();
        }

        changeAuthenticationDefinitionFragment(mAuthenticationDefinitionPatternFragment);
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
    public void setPresenter(AuthenticationDefinitionContract.Presenter presenter) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    @Override
    public void navigationToDashboardFromPassword(String credentialValue) {

        saveCredential(credentialValue, PASSWORD_TYPE);
    }

    @Override
    public void navigationToDashboardFromPattern(String credentialValue) {

        saveCredential(credentialValue, PATTERN_TYPE);

    }

    private void saveCredential(String credentialValue, int type) {

        Authentication authentication = new Authentication(credentialValue, type);
        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).update(authentication, deal -> {

            if (deal.getThrowable() != null) {
                throw new RuntimeException("Authentication has not been saved!");
            }
            navigateToDashboard();
        });
    }


    private void navigateToDashboard() {

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}