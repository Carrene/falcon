package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.DatePicker;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.transaction.customdatepicker.CustomDatePickerActivity;
import de.netalic.falcon.ui.transaction.customdatepicker.CustomDatePickerFragment;

public class TransactionHistoryFiltersPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, DatePickerDialog.OnDateSetListener {

    private ListPreference mListPreference;


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
            mListPreference = (android.support.v7.preference.ListPreference) pref;
            mListPreference.setSummary(mListPreference.getValue());
            if (mListPreference.getValue().equals("Custom")) {

                Intent intent=new Intent(getContext(),CustomDatePickerActivity.class);

                startActivity(intent);

                CustomDatePickerFragment.Callback callback=new CustomDatePickerFragment.Callback() {
                    @Override
                    public void sendStartAndEndDate(String start, String end) {

                        mListPreference.setSummary(start+""+end);
                    }
                };

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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
