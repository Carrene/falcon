package de.netalic.falcon.ui.transfer;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransferActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransferFragment transferFragment = (TransferFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transfer_fragmentcontainer);

        if (transferFragment == null) {

            transferFragment = TransferFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transferFragment, R.id.framelayout_transfer_fragmentcontainer);

        }

        new TransferPresenter(transferFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transfer_toolbar);
    }
}
