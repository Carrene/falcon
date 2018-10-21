package de.netalic.falcon.ui.transaction.customdatepicker;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class CustomDatePickerActivity extends BaseActivity implements CustomDatePickerFragment.Callback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        CustomDatePickerFragment customDatePickerFragment = (CustomDatePickerFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_customdatepicker_fragmentcontainer);

        if (customDatePickerFragment == null) {

            customDatePickerFragment = CustomDatePickerFragment.newInstance();

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), customDatePickerFragment, R.id.framelayout_customdatepicker_fragmentcontainer);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customdatepicker;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.customdatepicker_datepicker);
    }

    @Override
    public void sendStartAndEndDate(String start, String end) {

        String a = start + end;
    }
}
