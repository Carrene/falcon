package de.netalic.falcon.ui.charge;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeFailedActivity extends BaseActivity {

    private static final String ARGUMENT_RECEIPT = "receipt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {

            throw new RuntimeException("deposit should not null");
        }

        Receipt receipt = getIntent().getExtras().getParcelable(ARGUMENT_RECEIPT);


        ChargeFailedFragment chargeFailedFragment = (ChargeFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargefailed_fragmentcontainer);
        if (chargeFailedFragment == null) {
            chargeFailedFragment = ChargeFailedFragment.newInstance(receipt);
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
