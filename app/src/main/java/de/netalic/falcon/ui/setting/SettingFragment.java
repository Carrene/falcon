package de.netalic.falcon.ui.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;

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
        Preference preference;
        preference=findPreference("Login method");
        Authentication authentication=new Authentication();
        if (authentication.getAuthenticationType()==0) {
            preference.setSummary("Pattern");

        }
        else {

            preference.setSummary("Password");
        }
    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void initUiComponent() {


    }





}