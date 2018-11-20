package de.netalic.falcon.ui.addwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ListCurrencyActivity extends BaseActivity implements ListCurrencyFragment.Callback {

    private String mCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        Bundle bundle=getIntent().getExtras();
        mCurrency=bundle.getString(AddWalletFragment.SELECTED_CURRENCY);


        ListCurrencyFragment listCurrencyFragment = (ListCurrencyFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_listcurrency_fragmentcontainer);
        if (listCurrencyFragment == null) {

            listCurrencyFragment = ListCurrencyFragment.newInstance(mCurrency );
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

    @Override
    public void setCurrency(String currency) {

        mCurrency=currency;
    }


    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra(AddWalletFragment.SELECTED_CURRENCY,mCurrency);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:{

                Intent intent=new Intent();
                intent.putExtra(AddWalletFragment.SELECTED_CURRENCY,mCurrency);
                setResult(RESULT_OK,intent);
                finish();
                break;
            }
        }

        return true;
    }

}
