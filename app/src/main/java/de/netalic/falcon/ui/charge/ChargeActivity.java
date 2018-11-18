package de.netalic.falcon.ui.charge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class ChargeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this, getToolbar(),2);

        Bundle bundle=getIntent().getExtras();
        Wallet selectedWallet=bundle.getParcelable("wallet");

        ChargeFragment chargeFragment = (ChargeFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (chargeFragment == null) {
            chargeFragment = ChargeFragment.newInstance(selectedWallet);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chargeFragment, R.id.framelayout_charge_fragmentcontainer);
        }

        new ChargePresenter(chargeFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_charge;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.navigation_charge);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}
