package de.netalic.falcon.ui.transfer;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransferAmountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TransferAmountFragment transferAmountFragment=(TransferAmountFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferamount_fragmentcontainer);
        if (transferAmountFragment==null){

            transferAmountFragment=TransferAmountFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),transferAmountFragment,R.id.framelayout_transferamount_fragmentcontainer);
        }
        new TransferAmountPresenter(transferAmountFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transferamount;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transferamount_toolbar);
    }
}
