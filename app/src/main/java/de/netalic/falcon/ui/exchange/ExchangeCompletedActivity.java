package de.netalic.falcon.ui.exchange;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ExchangeCompletedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getExtras() == null) {

            throw new RuntimeException("Exchange should not be null");
        }

        Transaction transaction = getIntent().getExtras().getParcelable(ExchangeFragment.ARGUMENT_TRANSACTION);
        String amount = getIntent().getExtras().getString(ExchangeFragment.ARGUMENT_PAID_AMOUNT);
        ExchangeCompletedFragment exchangeCompletedFragment = (ExchangeCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_exchangecompleted_fragmentcontainer);

        if (exchangeCompletedFragment == null) {
            exchangeCompletedFragment = ExchangeCompletedFragment.newInstance(transaction, amount);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), exchangeCompletedFragment, R.id.framelayout_exchangecompleted_fragmentcontainer);
        }
        new ExchangeCompletedPresenter(exchangeCompletedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchangecompleted;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.exchangecompleted_title);
    }
}
