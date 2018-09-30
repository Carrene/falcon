package de.netalic.falcon.ui.setting.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.setting.authenticationdefinition.SettingAuthenticationDefinitionActivity;
import de.netalic.falcon.ui.setting.recoveryemail.SettingRecoveryEmailActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class SettingFragment extends PreferenceFragmentCompat implements SettingContract.View {

    private SettingContract.Presenter mSettingPresenter;
    Preference mLoginMethodPreference;
    Preference mRecoveryEmailPreference;

    @Override
    public void setPresenter(SettingContract.Presenter presenter) {

        mSettingPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setHasOptionsMenu(true);
        addPreferencesFromResource(R.xml.prefrences_setting);
        initUiComponent();
        mSettingPresenter.loginMethod();
        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor=new SharedPreferencesJwtPersistor(MyApp.getInstance());
        int id= (int)Parser.getTokenBody(sharedPreferencesJwtPersistor.get()).get("id");
        mSettingPresenter.recoveryEmail(id);
    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void initUiComponent() {

        mLoginMethodPreference = findPreference(getString(R.string.settingprefrence_loginmethodkey));





        mRecoveryEmailPreference=findPreference(getString(R.string.settingprefrence_recoveryemailkey));


    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        String key = preference.getKey();
        if (key.equals(getString(R.string.settingprefrence_loginmethodkey))) {
            Intent intent = new Intent(getContext(), SettingAuthenticationDefinitionActivity.class);
            startActivity(intent);
        } else if (key.equals(getString(R.string.settingprefrence_recoveryemailkey))) {
            Intent intent = new Intent(getContext(), SettingRecoveryEmailActivity.class);

                startActivity(intent);

        }
        else if (key.equals(getString(R.string.settingprefrence_aboutkey))){


        }
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void setMethodType(String type) {

        mLoginMethodPreference.setSummary(type);
    }

    @Override
    public void setRecoveryEmailState(String state) {

        mRecoveryEmailPreference.setTitle(state);
    }
}