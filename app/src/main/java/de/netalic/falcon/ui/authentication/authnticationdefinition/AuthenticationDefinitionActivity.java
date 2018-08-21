package de.netalic.falcon.ui.authentication.authnticationdefinition;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import info.hoang8f.android.segmented.SegmentedGroup;
import nuesoft.helpdroid.util.Converter;


public class AuthenticationDefinitionActivity extends BaseActivity implements AuthenticationDefinitionContract.View, AuthenticationDefinitionPasswordFragment.NavigateToDashboardCallback
        , AuthenticationDefinitionPatternFragment.NavigateToDashboardCallback {

    private SegmentedGroup mSegmentedGroup;
    private AuthenticationDefinitionPasswordFragment mAuthenticationDefinitionPasswordFragment;
    private AuthenticationDefinitionPatternFragment mAuthenticationDefinitionPatternFragment;

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


        saveCredential(credentialValue, 0);
    }

    @Override
    public void navigationToDashboardFromPattern(String credentialValue) {

        saveCredential(credentialValue, 1);

    }

    private void saveCredential(String credentialValue, int type) {

        byte[] credentialDigest = digestSha512(credentialValue);
        Authentication authentication = new Authentication(Converter.bytesToHexString(credentialDigest), type);
        AuthenticationRepository.getInstance().update(authentication, deal -> {

            if (deal.getModel() == null) {
                throw new RuntimeException("Authentication has not been saved!");
            }
            MyApp.sSensitiveRealmConfiguration.encryptionKey(credentialDigest).build();
            navigateToDashboard();

        });
    }

    private byte[] digestSha512(String value) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] digest = messageDigest.digest();
        return digest;
    }

    private void navigateToDashboard() {

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}