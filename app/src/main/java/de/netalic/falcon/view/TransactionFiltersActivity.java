package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.TransactionFiltersPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class TransactionFiltersActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransactionFiltersFragment  transactionFiltersFragment=(TransactionFiltersFragment)getSupportFragmentManager().findFragmentById(R.id.framelayout_transactionfilters_fragmentcontainer);
        if (transactionFiltersFragment==null){

            transactionFiltersFragment=TransactionFiltersFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),transactionFiltersFragment,R.id.framelayout_transactionfilters_fragmentcontainer);
        }
        new TransactionFiltersPresenter(transactionFiltersFragment);
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
