package de.netalic.falcon.ui.transaction.transactionhistory;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransactionDetailCompletedActivity extends BaseActivity {

    private static final String ARGUMENT_RECEIPT = "receipt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();
        if (getIntent().getExtras() == null) {

            throw new RuntimeException("deposit should not be null");
        }
        Receipt receipt = getIntent().getExtras().getParcelable(ARGUMENT_RECEIPT);


        TransactionDetailCompletedFragment transactiondetailCompletedFragment = (TransactionDetailCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transactiondetailcompleted_fragmentcontainer);
        if (transactiondetailCompletedFragment == null) {

            transactiondetailCompletedFragment = TransactionDetailCompletedFragment.newInstance(receipt);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transactiondetailCompletedFragment, R.id.framelayout_transactiondetailcompleted_fragmentcontainer);
        }

        new TransactionDetailCompletedPresenter(transactiondetailCompletedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transactiondetailcompleted;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transactiondetail_transactiondetail);
    }
}
