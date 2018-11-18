package de.netalic.falcon.ui.exchange;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;


public class ExchangeActivity extends BaseActivity {

    public static final String WALLET = "wallet";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this, getToolbar(), 1);

        ExchangeFragment exchangeFragment = (ExchangeFragment) getSupportFragmentManager().
                findFragmentById(R.id.framelayout_exchange_fragmentcontainer);

        if (exchangeFragment == null) {

            exchangeFragment = ExchangeFragment.newInstance(getIntent().getParcelableExtra(WALLET ));
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), exchangeFragment, R.id.
                    framelayout_exchange_fragmentcontainer);
        }

        new ExchangePresenter(exchangeFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.exchange_title);
    }
}
