package de.netalic.falcon.ui.send;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.util.ActivityUtil;

public class SendConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        if (getIntent().getExtras()==null){

            throw new RuntimeException("Transaction should not be null");
        }
        Bundle bundle=getIntent().getExtras();
        Transaction transaction=getIntent().getExtras().getParcelable(SendFragment.ARGUMENT_TRANSACTION);
        Wallet selectedWallet=bundle.getParcelable(DashboardFragment.SELECTED_WALLET);

        SendConfirmationFragment sendConfirmationFragment=(SendConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferconfirmation_fragmentcontainer);
        if (sendConfirmationFragment==null){

            sendConfirmationFragment=SendConfirmationFragment.newInstance(transaction,selectedWallet);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),sendConfirmationFragment,R.id.framelayout_transferconfirmation_fragmentcontainer);
        }
        new SendConfirmationPresenter(sendConfirmationFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sendconfirmation;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.sendconfirmation_toolbar);
    }
}
