package de.netalic.falcon.ui.charge;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ListCurrencyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBackButton();

        ListCurrencyFragment listCurrencyFragment = (ListCurrencyFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_listcurrency_fragmentcontainer);
        if (listCurrencyFragment == null) {

            listCurrencyFragment = ListCurrencyFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), listCurrencyFragment, R.id.framelayout_listcurrency_fragmentcontainer);
        }
        new ListCurrencyPresenter(listCurrencyFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_listcurrency;
    }

    @Override
    protected String getActionbarTitle() {
        return null;
    }
}
