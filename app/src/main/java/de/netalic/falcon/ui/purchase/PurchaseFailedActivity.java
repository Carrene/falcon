package de.netalic.falcon.ui.purchase;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class PurchaseFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PurchaseFailedFragment purchaseFailedFragment=(PurchaseFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_purchasefailed_fragmentcontainer);
        if (purchaseFailedFragment==null){

            purchaseFailedFragment=PurchaseFailedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),purchaseFailedFragment,R.id.framelayout_purchasefailed_fragmentcontainer);
        }

        new PurchaseFailedPresenter(purchaseFailedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchasefailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.purchasefailed_toolbar);
    }
}
