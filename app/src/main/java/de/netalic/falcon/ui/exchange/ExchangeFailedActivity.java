package de.netalic.falcon.ui.exchange;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ExchangeFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ExchangeFailedFragment sendFailedFragment=(ExchangeFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferfailed_fragmentcontainer);
        if (sendFailedFragment==null){

            sendFailedFragment=ExchangeFailedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),sendFailedFragment,R.id.framelayout_transferfailed_fragmentcontainer);

        }
        new ExchangeFailedPresenter(sendFailedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sendfailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.sendfailed_toolbar);
    }
}
