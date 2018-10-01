package de.netalic.falcon.ui.setting.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.setting.authenticationdefinition.SettingAuthenticationDefinitionActivity;
import de.netalic.falcon.ui.setting.recoveryemail.SettingRecoveryEmailActivity;


public class SettingFragment extends PreferenceFragmentCompat implements SettingContract.View {

    private SettingContract.Presenter mSettingPresenter;
    Preference mLoginMethodPreference;
    Preference mRecoveryEmailPreference;
    Preference mPhonePreference;

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
        mSettingPresenter.phoneNumber(getContext().getString(R.string.settingfragment_phone));
        mSettingPresenter.recoveryEmail(getContext().getString(R.string.settingfragment_email));
    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void initUiComponent() {

        mRecoveryEmailPreference = findPreference(getString(R.string.settingprefrence_recoveryemailkey));
        mLoginMethodPreference = findPreference(getString(R.string.settingprefrence_loginmethodkey));
        mPhonePreference = findPreference(getString(R.string.settingprefrence_phonenumberkey));


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
    public void setPatternType() {

        mLoginMethodPreference.setTitle(getContext().getString(R.string.settingfragment_pattern));
    }

    @Override
    public void setPasswordType() {

        mLoginMethodPreference.setTitle(getContext().getString(R.string.settingfragment_password));
    }

    @Override
    public void setRecoveryEmailState(String state) {

        mRecoveryEmailPreference.setTitle(state);
    }

    @Override
    public void setEmailNotSet() {

        mRecoveryEmailPreference.setTitle(getContext().getString(R.string.settingfragment_emailnotset));
    }

    @Override
    public void setPhoneNumber(String phone) {

        mPhonePreference.setTitle(phone);
    }

    @Override
    public void setPhoneNumberNotSet() {

        mPhonePreference.setTitle(getContext().getString(R.string.settingfragment_phonnumbernotset));
    }

}