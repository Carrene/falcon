package de.netalic.falcon.ui.transfer;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import static com.google.common.base.Preconditions.checkNotNull;

public class TransferConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        Bundle bundle=getIntent().getExtras();
        checkNotNull(bundle);
        int sourceWalletAddress=bundle.getInt(TransferAmountFragment.ARGUMENT_WALLET_ADDRESS);
        int destinationWalletAddress=bundle.getInt(TransferPayeeFragment.ARGUMENT_DESTINATION_WALLET_ADDRESS);
        double transferAmount=bundle.getDouble(TransferPayeeFragment.ARGUMENT_TRANSFER_AMOUNT);

        TransferConfirmationFragment transferConfirmationFragment=(TransferConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transferconfirmation_fragmentcontainer);
        if (transferConfirmationFragment==null){

            transferConfirmationFragment=TransferConfirmationFragment.newInstance(sourceWalletAddress,destinationWalletAddress,transferAmount);
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
