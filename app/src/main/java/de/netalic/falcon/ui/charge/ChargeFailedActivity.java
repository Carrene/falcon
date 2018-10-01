package de.netalic.falcon.ui.charge;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeFailedActivity extends BaseActivity {

    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {

            throw new RuntimeException("deposit should not null");
        }

        Transaction deposit = getIntent().getExtras().getParcelable(ARGUMENT_DEPOSIT);


        ChargeFailedFragment chargeFailedFragment = (ChargeFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargefailed_fragmentcontainer);
        if (chargeFailedFragment == null) {
            chargeFailedFragment = ChargeFailedFragment.newInstance(deposit);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chargeFailedFragment, R.id.framelayout_chargefailed_fragmentcontainer);
        }

        new ChargeFailedPresenter(chargeFailedFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_chargefailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.chargefailed_failed);
    }


}
