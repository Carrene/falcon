package de.netalic.falcon.ui.exchange;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ExchangeFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ExchangeFailedFragment exchangeFailedFragment = (ExchangeFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_exchangefailed_fragmentcontainer);
        if (exchangeFailedFragment == null) {

            exchangeFailedFragment = ExchangeFailedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), exchangeFailedFragment, R.id.framelayout_exchangefailed_fragmentcontainer);

        }
        new ExchangeFailedPresenter(exchangeFailedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchangefailed;
    }

    @Override
    protected String getActionbarTitle() {
        return "Exchange failed";
    }
}
