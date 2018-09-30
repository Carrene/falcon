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
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.ui.registration.recoveryemail.RecoveryEmailActivity;
import de.netalic.falcon.ui.setting.authenticationdefinition.SettingAuthenticationDefinitionActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class SettingFragment extends PreferenceFragmentCompat implements SettingContract.View {

    private SettingContract.Presenter mSettingPresenter;

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
    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void initUiComponent() {
        //TODO (Milad): All string should be in string
        Preference preference = findPreference(getString(R.string.settingprefrence_loginmethodkey));
        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).get(deal -> {
            if (deal.getThrowable() == null) {
                switch (deal.getModel().getAuthenticationType()) {
                    case Authentication.PATTERN_TYPE: {
                        preference.setTitle("Pattern");
                        break;
                    }

                    case Authentication.PASSWORD_TYPE: {
                        preference.setTitle("Password");
                        break;
                    }
                }
            }
        });

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());
        String phone = (String) tokenBody.get("phone");
        String email = (String) tokenBody.get("email");

        Preference phonePreference = findPreference(getString(R.string.settingprefrence_phonenumberkey));
        Preference emailPreference = findPreference(getString(R.string.settingprefrence_recoveryemailkey));
        phonePreference.setTitle(phone);
        if (email == null) {
            emailPreference.setTitle(getString(R.string.settingprefrence_emailnotset));
        } else {
            emailPreference.setTitle(email);
        }
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        String key = preference.getKey();
        if (key.equals(getString(R.string.settingprefrence_loginmethodkey))) {
            Intent intent = new Intent(getContext(), SettingAuthenticationDefinitionActivity.class);
            startActivity(intent);
        } else if (key.equals(getString(R.string.settingprefrence_recoveryemailkey))) {
//            Intent intent = new Intent(getContext(), SettingRecoveryEmailActivity.class);
//            SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance());
//            int id = (int) Parser.getTokenBody(sharedPreferencesJwtPersistor.get()).get("id");
//            RepositoryLocator.getInstance().getRepository(UserRepository.class).get(id, deal -> {
//                intent.putExtra(RecoveryEmailActivity.ARGUMENT_USER, deal.getModel());
//                startActivity(intent);
//            });
        }
        return super.onPreferenceTreeClick(preference);
    }
}