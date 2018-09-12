package de.netalic.falcon.ui.purchase;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class PurchaseCompletedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        PurchaseCompletedFragment purchaseCompletedFragment=(PurchaseCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_purchasecompleted_fragmentcontainer);
        if (purchaseCompletedFragment==null){

            purchaseCompletedFragment=PurchaseCompletedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),purchaseCompletedFragment,R.id.framelayout_purchasecompleted_fragmentcontainer);
        }

        new PurchaseCompletedPresenter(purchaseCompletedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchasecompleted;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.purchasecompleted_toolbar);
    }
}
