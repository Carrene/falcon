package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeCompletedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeCompletedActivity extends BaseActivity {

    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras()==null){

            throw new RuntimeException("deposit should not be null");
        }
        Deposit deposit=getIntent().getExtras().getParcelable(ARGUMENT_DEPOSIT);

        ChargeCompletedFragment chargeCompletedFragment=(ChargeCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargecompleted_fragmentcontainer);
        if (chargeCompletedFragment==null){

            chargeCompletedFragment=ChargeCompletedFragment.newInstance(deposit);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),chargeCompletedFragment,R.id.framelayout_chargecompleted_fragmentcontainer);
        }

        new ChargeCompletedPresenter(chargeCompletedFragment);

    }


    @Override
    protected int getLayoutId() {

        return R.layout.activity_chargecompleted;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.chargecompleted_chargecompleted);
    }
}
