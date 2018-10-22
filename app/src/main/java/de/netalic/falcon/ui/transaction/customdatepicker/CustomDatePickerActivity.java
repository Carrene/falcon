package de.netalic.falcon.ui.transaction.customdatepicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.transaction.transactionhistoryfilters.TransactionHistoryFiltersActivity;
import de.netalic.falcon.util.ActivityUtil;

public class CustomDatePickerActivity extends BaseActivity implements CustomDatePickerFragment.Callback {

    private String mStartDate;
    private String mEndDate;

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

       mStartDate=start;
       mEndDate=end;
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra(TransactionHistoryFiltersActivity.START_AND_END_DATE, mStartDate);
        intent.putExtra("a", mEndDate);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {

                Intent intent = new Intent();
                intent.putExtra(TransactionHistoryFiltersActivity.START_AND_END_DATE, mStartDate);
                intent.putExtra("a", mEndDate);
                setResult(RESULT_OK, intent);
                finish();
                break;
            }
        }

        return true;
    }
}
