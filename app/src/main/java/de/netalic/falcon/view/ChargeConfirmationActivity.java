package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargeConfirmationPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ChargeConfirmationFragment chargeConfirmationFragment = (ChargeConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargeconfirmation_fragmentcontainer);
        if (chargeConfirmationFragment == null) {

            chargeConfirmationFragment = ChargeConfirmationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chargeConfirmationFragment, R.id.framelayout_chargeconfirmation_fragmentcontainer);
        }
        new ChargeConfirmationPresenter(chargeConfirmationFragment);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chargeconfirmation;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.chargeconfirmation_chargeconfirmation);
    }
}
