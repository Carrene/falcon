package de.netalic.falcon.ui.setting.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
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
        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance());
        int id = (int) Parser.getTokenBody(sharedPreferencesJwtPersistor.get()).get("id");
        mSettingPresenter.recoveryEmail(id);
    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void initUiComponent() {

        mRecoveryEmailPreference = findPreference(getString(R.string.settingprefrence_recoveryemailkey));
        mLoginMethodPreference = findPreference(getString(R.string.settingprefrence_loginmethodkey));
        //TODO (Milad): All string should be in string

//        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
//        Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());
//        String phone = (String) tokenBody.get("phone");
//        String email = (String) tokenBody.get("email");

        //TODO(Milad) Move phone to presenter
        Preference phonePreference = findPreference(getString(R.string.settingprefrence_phonenumberkey));
//        phonePreference.setTitle(phone);

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

        } else if (key.equals(getString(R.string.settingprefrence_aboutkey))) {

        }

        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void setMethodType(String type) {

        mLoginMethodPreference.setTitle(type);
    }

    @Override
    public void setRecoveryEmailState(String state) {

        mRecoveryEmailPreference.setTitle(state);
    }
}