package de.netalic.falcon.ui.setting;

import android.os.Bundle;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.TextView;

import de.netalic.falcon.R;

public class SettingFragment extends PreferenceFragmentCompat implements SettingContract.View {

    private SettingContract.Presenter mSettingPresenter;
    private TextView mTextViewRewcoveryEmail;


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

        addPreferencesFromResource(R.xml.prefrences_setting);
        EditTextPreference editTextPreference=(EditTextPreference) findPreference("recoveryemail");
        editTextPreference.setOnPreferenceChangeListener((preference, newValue) -> {

            preference.setTitle(newValue.toString());
            return false;
        });
        initUiComponent();

    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    private void initUiComponent() {


    }
}

