package de.netalic.falcon.ui.charge;

import android.content.Intent;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ListCurrencyActivity extends BaseActivity {

    private String mCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBackButton();



        Intent intent = new Intent();
        intent.putExtra("currency", mCurrency);
        setResult(RESULT_OK, intent);
        finish();


        Bundle bundle=getIntent().getExtras();
        String selectedCurrency=bundle.getString(AddWalletFragment.SELECTED_CURRENCY);


        ListCurrencyFragment listCurrencyFragment = (ListCurrencyFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_listcurrency_fragmentcontainer);
        if (listCurrencyFragment == null) {

            listCurrencyFragment = ListCurrencyFragment.newInstance(selectedCurrency );
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), listCurrencyFragment, R.id.framelayout_listcurrency_fragmentcontainer);
        }
        new ListCurrencyPresenter(listCurrencyFragment);
    }

    public void setCurrency(String currency){

        mCurrency=currency;
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
