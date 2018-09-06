package de.netalic.falcon.ui.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.View;

import de.netalic.falcon.R;

public class SettingFragment extends PreferenceFragmentCompat implements SettingContract.View {

    private SettingContract.Presenter mSettingPresenter;




    @Override
    public void setPresenter(SettingContract.Presenter presenter) {

        mSettingPresenter=presenter;
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
       
    }

    public static SettingFragment newInstance() {

        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

}
