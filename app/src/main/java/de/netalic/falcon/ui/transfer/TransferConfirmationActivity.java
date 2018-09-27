package de.netalic.falcon.ui.transfer;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransferConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        if (getIntent().getExtras()==null){

            throw new RuntimeException("Transaction should not be null");
        }
        Transaction transaction=getIntent().getExtras().getParcelable(TransferPayeeFragment.ARGUMENT_TRANSACTION);

        TransferConfirmationFragment transferConfirmationFragment=(TransferConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferconfirmation_fragmentcontainer);
        if (transferConfirmationFragment==null){

            transferConfirmationFragment=TransferConfirmationFragment.newInstance(transaction);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),transferConfirmationFragment,R.id.framelayout_transferconfirmation_fragmentcontainer);
        }
        new TransferConfirmationPresenter(transferConfirmationFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transferconfirmation;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transferconfirmation_toolbar);
    }
}
