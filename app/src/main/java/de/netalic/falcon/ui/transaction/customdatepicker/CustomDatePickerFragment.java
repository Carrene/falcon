package de.netalic.falcon.ui.transaction.customdatepicker;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import de.netalic.falcon.R;

public class CustomDatePickerFragment extends PreferenceFragmentCompat {

    private Preference mPreferenceStart;
    private Preference mPreferenceEnd;
    
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.prefrences_customdatepicker);
        initUiComponent();

    }

    public static CustomDatePickerFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CustomDatePickerFragment fragment = new CustomDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initUiComponent(){

        mPreferenceStart=findPreference(getString(R.string.customdatepicker_choosestartdatekey));
        mPreferenceEnd=findPreference(getString(R.string.customdatepicker_chooseenddatekey));
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        String key=preference.getKey();

        if (key.equals(R.string.customdatepicker_choosestartdatekey)){


        }

        else if (key.equals(R.string.customdatepicker_chooseenddatekey)){



        }
        return super.onPreferenceTreeClick(preference);
    }
}
