package de.netalic.falcon.ui.transaction.transactionhistory;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class TransactionHistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this,getToolbar(),5);

        TransactionHistoryFragment transactionHistoryFragment=(TransactionHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transactionhistory_fragmentcontainer);
        if (transactionHistoryFragment==null){

            transactionHistoryFragment=TransactionHistoryFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),transactionHistoryFragment,R.id.framelayout_transactionhistory_fragmentcontainer);

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
}
