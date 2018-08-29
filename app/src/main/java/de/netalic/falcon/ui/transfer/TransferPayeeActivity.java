package de.netalic.falcon.ui.transfer;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransferPayeeActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBackButton();

        TransferPayeeFragment transferPayeeFragment = (TransferPayeeFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferpayee_fragmentcontainer);
        if (transferPayeeFragment == null) {

            transferPayeeFragment = TransferPayeeFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transferPayeeFragment, R.id.framelayout_transferpayee_fragmentcontainer);

        }

        new TransferPayeePresenter(transferPayeeFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transferpayee;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transferpayee_toolbar);
    }
}
