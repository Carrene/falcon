package de.netalic.falcon.ui.send;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class SendActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        NavigationDrawerUtil.getDrawer(this,getToolbar(),1);

        SendFragment sendFragment=(SendFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_send_fragmentcontainer);

        if (sendFragment==null) {
            sendFragment = SendFragment.newInstance();
            ActivityUtil.replaceFragmentWithFragment(getSupportFragmentManager(), sendFragment, R.id.framelayout_send_fragmentcontainer);
        }
        new SendPresenter(sendFragment);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_send;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.send_title);
    }
}
