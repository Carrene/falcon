package de.netalic.falcon.ui.transaction.customdatepicker;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import de.netalic.falcon.R;

public class CustomDatePickerFragment extends PreferenceFragmentCompat implements DatePickerDialog.OnDateSetListener {

    private Preference mPreferenceStart;
    private Preference mPreferenceEnd;
    private DatePickerDialog mDatePickerDialog;
    private String mStartDate;
    private String mEndDate;


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        setPreferencesFromResource(R.xml.prefrences_customdatepicker, rootKey);
        mDatePickerDialog = new DatePickerDialog(getContext(), this, 2018, 1, 1);
        initUiComponent();


    }

    public static CustomDatePickerFragment newInstance() {

        Bundle args = new Bundle();

        CustomDatePickerFragment fragment = new CustomDatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void initUiComponent() {

        mPreferenceStart = findPreference(getString(R.string.customdatepicker_choosestartdatekey));
        mPreferenceEnd = findPreference(getString(R.string.customdatepicker_chooseenddatekey));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        String key = preference.getKey();

        if (key.equals(getString(R.string.customdatepicker_choosestartdatekey))) {

            mDatePickerDialog.show();
            mDatePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {


                mStartDate = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(dayOfMonth);

                preference.setSummary(mStartDate);

                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("custom", mStartDate +"_"+ mEndDate).apply();

            });


        } else if (key.equals(getString(R.string.customdatepicker_chooseenddatekey))) {

            mDatePickerDialog.show();
            mDatePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {

                mEndDate = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(dayOfMonth);
                preference.setSummary(mEndDate);
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("custom", mStartDate+"_"+ mEndDate).apply();

            });
        }
        return super.onPreferenceTreeClick(preference);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}