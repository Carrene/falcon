package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.widget.DatePicker;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.transaction.customdatepicker.CustomDatePickerActivity;

public class TransactionHistoryFiltersPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, DatePickerDialog.OnDateSetListener {

    private ListPreference mListPreference;
    private String mStartAndEndDate;
    private Preference mPreference;


    public static TransactionHistoryFiltersPreferenceFragment newInstance() {

        TransactionHistoryFiltersPreferenceFragment fragment = new TransactionHistoryFiltersPreferenceFragment();

        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.prefrences_transactionfilters, rootKey);

        initUiComponent();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        Preference pref = findPreference(key);
        if (pref instanceof android.support.v7.preference.ListPreference) {
            mListPreference = (android.support.v7.preference.ListPreference) pref;
            mListPreference.setSummary(mListPreference.getValue());
            if (mListPreference.getValue().equals("Custom")) {

                Intent intent = new Intent(getContext(), CustomDatePickerActivity.class);

                startActivityForResult(intent, 1);
//                mListPreference.setSummary(mStartAndEndDate);
            }
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        mPreference=getPreferenceManager().findPreference("date");
        String a=mPreference.getSummary().toString();
        mListPreference.setSummary(a);

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

    private void initUiComponent() {

        mListPreference = (ListPreference) findPreference("date");

    }
}
