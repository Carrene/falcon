package de.netalic.falcon.ui.charge;

import android.content.Intent;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeCompletedActivity extends BaseActivity {

    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {

            throw new RuntimeException("deposit should not be null");
        }
        Transaction transaction = getIntent().getExtras().getParcelable(ARGUMENT_DEPOSIT);

        ChargeCompletedFragment chargeCompletedFragment = (ChargeCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargecompleted_fragmentcontainer);
        if (chargeCompletedFragment == null) {

            chargeCompletedFragment = ChargeCompletedFragment.newInstance(transaction);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chargeCompletedFragment, R.id.framelayout_chargecompleted_fragmentcontainer);
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

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
