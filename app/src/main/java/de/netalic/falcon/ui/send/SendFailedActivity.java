package de.netalic.falcon.ui.send;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.transfer.TransferFailedFragment;
import de.netalic.falcon.ui.transfer.TransferFailedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class SendFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SendFailedFragment sendFailedFragment=(SendFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferfailed_fragmentcontainer);
        if (sendFailedFragment==null){

            sendFailedFragment=SendFailedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),sendFailedFragment,R.id.framelayout_transferfailed_fragmentcontainer);

        }
        new SendFailedPresenter(sendFailedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transferfailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transferfailed_toolbar);
    }
}
