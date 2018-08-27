package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransactionHistoryFiltersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setupBackButton();

//        TransactionHistoryFiltersFragment transactionHistoryFiltersFragment = (TransactionHistoryFiltersFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transactionfilters_fragmentcontainer);
//        if (transactionHistoryFiltersFragment == null) {
//
//            transactionHistoryFiltersFragment = TransactionHistoryFiltersFragment.newInstance();
//            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transactionHistoryFiltersFragment, R.id.framelayout_transactionfilters_fragmentcontainer);
//        }
//        new TransactionHistoryFiltersPresenter(transactionHistoryFiltersFragment);

//        TransactionHistoryFiltersPrefrenceFragment transactionHistoryFiltersPrefrenceFragment = getSupportFragmentManager().findFragmentById(R.id.framelayout_transactionfilters_fragmentcontainer);
        ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), new TransactionHistoryFiltersPrefrenceFragment(), R.id.framelayout_transactionfilters_fragmentcontainer);

    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_transactionfilters;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.transactionfilters_toolbar);
    }


}
