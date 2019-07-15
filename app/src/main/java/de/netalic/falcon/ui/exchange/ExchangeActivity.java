package de.netalic.falcon.ui.exchange;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.addwallet.AddWalletFragment;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;


public class ExchangeActivity extends BaseActivity {

    public static final String WALLET = "wallet";
    private Rate mCurrency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this, getToolbar(), 1);

        ExchangeFragment exchangeFragment = (ExchangeFragment) getSupportFragmentManager().
                findFragmentById(R.id.framelayout_exchange_fragmentcontainer);

        if (exchangeFragment == null) {

            exchangeFragment = ExchangeFragment.newInstance(getIntent().getParcelableExtra(WALLET));
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), exchangeFragment, R.id.
                    framelayout_exchange_fragmentcontainer);
        }

        new ExchangePresenter(exchangeFragment);
    }

    public Rate getCurrency() {

        return mCurrency;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        mCurrency = data.getParcelableExtra(AddWalletFragment.SELECTED_CURRENCY);

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
