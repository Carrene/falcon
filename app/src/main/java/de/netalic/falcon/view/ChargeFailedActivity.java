package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeFailedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeFailedActivity extends BaseActivity {

    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Deposit deposit=getIntent().getExtras().getParcelable(ARGUMENT_DEPOSIT);


        ChargeFailedFragment chargeFailedFragment=(ChargeFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargefailed_fragmentcontainer);
        if (chargeFailedFragment==null){
            chargeFailedFragment=ChargeFailedFragment.newInstance(deposit);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),chargeFailedFragment,R.id.framelayout_chargefailed_fragmentcontainer);
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
