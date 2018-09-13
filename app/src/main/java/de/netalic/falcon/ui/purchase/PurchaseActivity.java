package de.netalic.falcon.ui.purchase;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class PurchaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this,getToolbar(),1);


        PurchaseFragment purchaseFragment=(PurchaseFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_purchase_fragmentcontainer);
        if (purchaseFragment==null){

            purchaseFragment=PurchaseFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),purchaseFragment,R.id.framelayout_purchase_fragmentcontainer);
        }

        new PurchasePresenter(purchaseFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchase;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.purchase_toolbar);
    }
}
