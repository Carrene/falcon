package de.netalic.falcon.ui.transaction.transactionhistory;

import android.content.Intent;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.ui.transaction.transactionhistoryfilters.TransactionHistoryFiltersActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class TransactionHistoryActivity extends BaseActivity {

    private String mStartDate;
    private String mEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this, getToolbar(), 5);

        TransactionHistoryFragment transactionHistoryFragment = (TransactionHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transactionhistory_fragmentcontainer);
        if (transactionHistoryFragment == null) {

            transactionHistoryFragment = TransactionHistoryFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transactionHistoryFragment, R.id.framelayout_transactionhistory_fragmentcontainer);

        }

        new TransactionHistoryPresenter(transactionHistoryFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_transactionhistory;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.transactionhistory_toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }

    public String getStartDate(){

        return mStartDate;
    }

    public String getEndDate(){

        return mEndDate;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mStartDate = data.getStringExtra(TransactionHistoryFiltersActivity.START_AND_END_DATE);
        mEndDate = data.getStringExtra("a");
    }
}
