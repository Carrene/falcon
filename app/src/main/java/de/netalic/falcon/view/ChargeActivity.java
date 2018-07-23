package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ChargeFragment chargeFragment = (ChargeFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (chargeFragment == null) {
            chargeFragment = ChargeFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chargeFragment, R.id.framelayout_charge_fragmentcontainer);
        }
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_charge;
    }

    @Override
    protected String getActionbarTitle() {

        return "test";
    }
}
