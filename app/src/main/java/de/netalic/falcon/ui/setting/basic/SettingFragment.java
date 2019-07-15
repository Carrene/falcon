package de.netalic.falcon.ui.setting.basic;

import android.content.Intent;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.setting.authenticationdefinition.SettingAuthenticationDefinitionActivity;
import de.netalic.falcon.ui.setting.basecurrency.SettingBaseCurrencyActivity;
import de.netalic.falcon.ui.setting.recoveryemail.SettingRecoveryEmailActivity;


public class SettingFragment extends PreferenceFragmentCompat implements SettingContract.View {

    private SettingContract.Presenter mSettingPresenter;
    private Preference mPreferenceLoginMethod;
    private Preference mPreferenceRecoveryEmail;
    private Preference mPreferencePhone;
    private Preference mPreferenceBaseCurrency;

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
        mSettingPresenter.getUserPhone();
        mSettingPresenter.loginMethod();
        mSettingPresenter.phoneNumber(getContext().getString(R.string.settingfragment_phone));
        mSettingPresenter.recoveryEmail(getContext().getString(R.string.settingfragment_email));
        mSettingPresenter.baseCurrency();
    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void initUiComponent() {

        mPreferenceRecoveryEmail = findPreference(getString(R.string.settingprefrence_recoveryemailkey));
        mPreferenceLoginMethod = findPreference(getString(R.string.settingprefrence_loginmethodkey));
        mPreferencePhone = findPreference(getString(R.string.settingprefrence_phonenumberkey));
        mPreferenceBaseCurrency = findPreference(getContext().getString(R.string.settingprefrence_basecurrencykey));


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

        } else if (key.equals(getContext().getString(R.string.settingprefrence_basecurrencykey))) {

            Intent intent = new Intent(getContext(), SettingBaseCurrencyActivity.class);
            startActivity(intent);

        }

        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public void setPatternType() {

        new Runnable() {
            @Override
            public void run() {

                mPreferenceLoginMethod.setTitle(getContext().getString(R.string.settingfragment_pattern));
            }
        };

    }

    @Override
    public void setPasswordType() {

        mPreferenceLoginMethod.setTitle(getContext().getString(R.string.settingfragment_password));
    }

    @Override
    public void setRecoveryEmailState(String state) {

        mPreferenceRecoveryEmail.setTitle(state);
    }

    @Override
    public void setEmailNotSet() {

        mPreferenceRecoveryEmail.setTitle(getContext().getString(R.string.settingfragment_emailnotset));
    }

    @Override
    public void setPhoneNumber(String phone) {

        mPreferencePhone.setTitle(phone);
    }

    @Override
    public void setPhoneNumberNotSet() {

        mPreferencePhone.setTitle(getContext().getString(R.string.settingfragment_phonnumbernotset));
    }

    @Override
    public void setBaseCurrency(String currency) {

        new Runnable() {
            @Override
            public void run() {

                mPreferenceBaseCurrency.setTitle(currency);
            }
        };


    }

    @Override
    public void setBaseCurrencyNotSet() {

        mPreferenceBaseCurrency.setTitle(getContext().getString(R.string.settingfragment_basecurrencynotset));
    }

    public void getTitleOfActivityToolbar(String phone) {
        ((SettingActivity) getActivity()).setName(phone);
    }


}