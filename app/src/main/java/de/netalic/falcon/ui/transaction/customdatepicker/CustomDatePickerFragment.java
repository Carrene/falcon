package de.netalic.falcon.ui.transaction.customdatepicker;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.DatePicker;

import java.util.Map;

import de.netalic.falcon.R;

public class CustomDatePickerFragment extends PreferenceFragmentCompat implements DatePickerDialog.OnDateSetListener {

    private Preference mPreferenceStart;
    private Preference mPreferenceEnd;
    private DatePickerDialog mDatePickerDialog;
    private Callback mCallback;
    private String mStartDate;
    private String mEndDate;
    private int mFindOutWhenCanCallback = 0;
    private Map<String, ?> mFilterMap;

    public interface Callback {

        void sendStartAndEndDate(String start, String end);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.prefrences_customdatepicker);
        mDatePickerDialog = new DatePickerDialog(getContext());
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

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean onPreferenceTreeClick(Preference preference) {

        String key = preference.getKey();

        if (key.equals(getString(R.string.customdatepicker_choosestartdatekey))) {

            mDatePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {


                mStartDate = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(dayOfMonth);
                mPreferenceStart.setSummary(mStartDate);
                mFindOutWhenCanCallback = mFindOutWhenCanCallback + 1;
                if (mFindOutWhenCanCallback == 2) {
                    mCallback.sendStartAndEndDate(mStartDate, mEndDate);

                }
            });
            mDatePickerDialog.show();


        } else if (key.equals(getString(R.string.customdatepicker_chooseenddatekey))) {

            mDatePickerDialog.setOnDateSetListener((view, year, month, dayOfMonth) -> {

                mEndDate = Integer.toString(year) + "/" + Integer.toString(month) + "/" + Integer.toString(dayOfMonth);
                mPreferenceEnd.setSummary(mStartDate);
                mFindOutWhenCanCallback = mFindOutWhenCanCallback + 1;
                if (mFindOutWhenCanCallback == 2) {
                    mCallback.sendStartAndEndDate(mStartDate, mEndDate);

                }
            });
            mDatePickerDialog.show();

        }

        return super.onPreferenceTreeClick(preference);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }


}