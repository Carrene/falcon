package de.netalic.falcon.ui.setting;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;

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
        //TODO (Milad) : Get authentication from database not creating Authentication object!
        Preference preference = findPreference("Login method");
        Authentication authentication = new Authentication();
        switch (authentication.getAuthenticationType()) {
            case Authentication.PATTERN_TYPE: {
                preference.setSummary("Pattern");
                break;
            }

            case Authentication.PASSWORD_TYPE: {
                preference.setSummary("Password");
                break;
            }
        }
    }
}