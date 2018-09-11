package de.netalic.falcon.ui.purchase;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class PurchaseConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        PurchaseConfirmationFragment purchaseConfirmationFragment = (PurchaseConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_purchaseconfirmation_fragmentcontainer);
        if (purchaseConfirmationFragment == null) {

            purchaseConfirmationFragment = PurchaseConfirmationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), purchaseConfirmationFragment, R.id.framelayout_purchaseconfirmation_fragmentcontainer);
        }

        new PurchaseActivityPresenter(purchaseConfirmationFragment);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchaseconfirmation;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.purchaseconfirmation_toolbar);
    }
}
