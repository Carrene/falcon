package de.netalic.falcon.ui.transfer;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransferCompletedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getExtras()==null){

            throw new RuntimeException("transaction should not be null");
        }

        Transaction transaction=getIntent().getExtras().getParcelable(TransferPayeeFragment.ARGUMENT_TRANSACTION);
        TransferCompletedFragment transferCompletedFragment=(TransferCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transfercompleted_fragmentcontainer);
        if (transferCompletedFragment==null){

            transferCompletedFragment=TransferCompletedFragment.newInstance(transaction);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),transferCompletedFragment,R.id.framelayout_transfercompleted_fragmentcontainer);
        }
        new TransferCompletedPresenter(transferCompletedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfercompleted;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transfercompleted_toolbar);
    }
}
