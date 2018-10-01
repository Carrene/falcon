package de.netalic.falcon.ui.charge;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeConfirmationActivity extends BaseActivity {

    public static final String ARGUMENT_CHARGE_START = "chargeStart";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {
            throw new RuntimeException("Charge response should not be null!");
        }

        setupBackButton();

        Bundle bundle = getIntent().getExtras();
        Transaction transaction = bundle.getParcelable(ARGUMENT_CHARGE_START);

        ChargeConfirmationFragment chargeConfirmationFragment = (ChargeConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargeconfirmation_fragmentcontainer);
        if (chargeConfirmationFragment == null) {

            chargeConfirmationFragment = ChargeConfirmationFragment.newInstance(transaction);
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
