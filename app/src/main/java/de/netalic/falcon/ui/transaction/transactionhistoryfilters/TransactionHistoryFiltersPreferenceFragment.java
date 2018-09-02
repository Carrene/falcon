package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.DatePicker;

import de.netalic.falcon.R;

public class TransactionHistoryFiltersPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, DatePickerDialog.OnDateSetListener {

    public static TransactionHistoryFiltersPreferenceFragment newInstance() {

        TransactionHistoryFiltersPreferenceFragment fragment = new TransactionHistoryFiltersPreferenceFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.prefrences_transactionfilters);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        Preference pref = findPreference(key);
        if (pref instanceof android.support.v7.preference.ListPreference) {
            ListPreference listPreference = (android.support.v7.preference.ListPreference) pref;
            listPreference.setSummary(listPreference.getValue());
            if (listPreference.getValue().equals("Custom")) {
                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(getContext(), this, 2018, 9, 10);
                datePickerDialog.show();
            }
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {

        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

}
