package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransactionHistoryFiltersActivity extends BaseActivity {

    private String mStartDate;
    private String mEndDate;
    public static final String START_AND_END_DATE = "startAndEndDate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setupBackButton();

        TransactionHistoryFiltersPreferenceFragment transactionHistoryFiltersPreferenceFragment = (TransactionHistoryFiltersPreferenceFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transactionfilters_fragmentcontainer);
        if (transactionHistoryFiltersPreferenceFragment == null) {
            transactionHistoryFiltersPreferenceFragment = TransactionHistoryFiltersPreferenceFragment.newInstance();
        }
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transactionHistoryFiltersPreferenceFragment, R.id.framelayout_transactionfilters_fragmentcontainer);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_transactionfilters;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.transactionfilters_toolbar);
    }

    public String getStartAndEndDate() {


        return mStartDate+"_"+mEndDate;
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

//                Intent intent = new Intent();
//                intent.putExtra(TransactionHistoryFiltersActivity.START_AND_END_DATE, mStartDate);
//                intent.putExtra("a", mEndDate);
//                setResult(RESULT_OK, intent);
//                finish();
//                break;
                        super.onBackPressed();
        Intent intent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, intent);
                break;
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mStartDate = data.getStringExtra(START_AND_END_DATE);
        mEndDate=data.getStringExtra("a");
    }
}

