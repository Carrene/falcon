package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.transaction.transactionhistory.TransactionHistoryActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransactionHistoryFiltersActivity extends BaseActivity {

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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, intent);
    }
}
