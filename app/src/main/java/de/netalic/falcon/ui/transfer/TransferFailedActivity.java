package de.netalic.falcon.ui.transfer;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransferFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        TransferFailedFragment transferFailedFragment=(TransferFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferfailed_fragmentcontainer);
        if (transferFailedFragment==null){

            transferFailedFragment=TransferFailedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),transferFailedFragment,R.id.framelayout_transferfailed_fragmentcontainer);

        }
        new TransferFailedPresenter(transferFailedFragment);
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
