package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargeCompletedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeCompletedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ChargeCompletedFragment chargeCompletedFragment=(ChargeCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargecompleted_fragmentcontainer);
        if (chargeCompletedFragment==null){

            chargeCompletedFragment=ChargeCompletedFragment.newInstance();
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
