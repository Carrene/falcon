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


        PurchaseAmountFragment purchaseAmountFragment=(PurchaseAmountFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_purchaseamount_fragmentcontainer);
        if (purchaseAmountFragment==null){

            purchaseAmountFragment=PurchaseAmountFragment.newInstance();
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
