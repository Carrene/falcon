package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;

import com.mikepenz.materialdrawer.Drawer;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargeAmountPresenter;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class ChargeAmountActivity extends BaseActivity {

    public static final String ARGUMENT_USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        if (getIntent().getExtras() == null) {
//            throw new RuntimeException("User should not be null!");
//        }

        Drawer result = NavigationDrawerUtil.getDrawer(this, getToolbar(),2);
//        User user = getIntent().getExtras().getParcelable(ARGUMENT_USER);

        ChargeAmountFragment chargeAmountFragment = (ChargeAmountFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (chargeAmountFragment == null) {
            chargeAmountFragment = ChargeAmountFragment.newInstance();
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

        return getString(R.string.charge_toolbartitle);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, intent);
    }
}
