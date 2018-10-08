package de.netalic.falcon.ui.purchase;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class PurchaseAmountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();
        Bundle bundle=getIntent().getExtras();
        String currencyCode=bundle.getString(PurchaseFragment.CURRENCY_CODE);
        String walletAddress=bundle.getString(PurchaseFragment.WALLET_ADDRESS);


        PurchaseAmountFragment purchaseAmountFragment=(PurchaseAmountFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_purchaseamount_fragmentcontainer);
        if (purchaseAmountFragment==null){

            purchaseAmountFragment=PurchaseAmountFragment.newInstance(walletAddress,currencyCode);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),purchaseAmountFragment,R.id.framelayout_purchaseamount_fragmentcontainer);
        }
        new PurchaseAmountPresenter(purchaseAmountFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchaseamount;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.purchaseamount_toolbar);
    }
}
