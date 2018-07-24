package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargePresenter;
import de.netalic.falcon.presenter.DashboardPresenter;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class ChargeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this, getToolbar(),2);

        ChargeFragment chargeFragment = (ChargeFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (chargeFragment == null) {
            chargeFragment = ChargeFragment.newInstance();
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
}
