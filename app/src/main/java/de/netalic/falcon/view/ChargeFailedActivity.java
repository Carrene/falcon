package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargeFailedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChargeFailedFragment chargeFailedFragment=(ChargeFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargefailed_fragmentcontainer);
        if (chargeFailedFragment==null){
            chargeFailedFragment=ChargeFailedFragment.newInstance();
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
