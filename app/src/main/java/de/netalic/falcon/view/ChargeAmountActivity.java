package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;

import com.mikepenz.materialdrawer.Drawer;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargeAmountPresenter;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class ChargeAmountActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //TODO: (Ehsan) get walletId and paymentGatewayName when back is pressed
        if (getIntent().getExtras() == null) {
            throw new RuntimeException("User should not be null!");
        }

        setupBackButton();


        Bundle bundle = getIntent().getExtras();

        int walletId = bundle.getInt("walletId");
        String paymentGatewayName = bundle.getString("paymentGatewayName");

        ChargeAmountFragment chargeAmountFragment = (ChargeAmountFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (chargeAmountFragment == null) {
            chargeAmountFragment = ChargeAmountFragment.newInstance(walletId, paymentGatewayName);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chargeAmountFragment, R.id.framelayout_charge_fragmentcontainer);
        }
        new ChargeAmountPresenter(chargeAmountFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_charge_amount;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.chargeamount_toolbartitle);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, intent);
    }
}
