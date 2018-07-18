package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.ChargePresenter;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeActivity extends BaseActivity {

    public static final String ARGUMENT_USER = "USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {
            throw new RuntimeException("User should not be null!");
        }

        User user = getIntent().getExtras().getParcelable(ARGUMENT_USER);

        ChargeFragment chargeFragment = (ChargeFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (chargeFragment == null) {
            chargeFragment = ChargeFragment.newInstance(user);
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

        return getString(R.string.charge_toolbartitle);
    }
}
