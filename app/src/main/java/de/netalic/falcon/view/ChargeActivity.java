package de.netalic.falcon.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargePresenter;
import de.netalic.falcon.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ChargeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        ChargeFragment  chargeFragment=(ChargeFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (chargeFragment==null){

            chargeFragment=ChargeFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),chargeFragment,R.id.framelayout_charge_fragmentcontainer);
        }
        new ChargePresenter(chargeFragment);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
