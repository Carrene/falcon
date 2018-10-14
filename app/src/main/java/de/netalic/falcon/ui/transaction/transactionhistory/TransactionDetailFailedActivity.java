package de.netalic.falcon.ui.transaction.transactionhistory;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class TransactionDetailFailedActivity extends BaseActivity {

    private static final String ARGUMENT_RECEIPT = "receipt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();
        if (getIntent().getExtras() == null) {

            throw new RuntimeException("deposit should not null");
        }

        Bundle bundle=getIntent().getExtras();

        String trnsactionType=bundle.getString(TransactionHistoryRecyclerViewAdapter.TRANSACTION_TYPE);
        Receipt receipt = getIntent().getExtras().getParcelable(ARGUMENT_RECEIPT);


        TransactionDetailFailedFragment transactiondetailFailedFragment = (TransactionDetailFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transactiondetailfailed_fragmentcontainer);
        if (transactiondetailFailedFragment == null) {
            transactiondetailFailedFragment = TransactionDetailFailedFragment.newInstance(receipt,trnsactionType);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transactiondetailFailedFragment, R.id.framelayout_transactiondetailfailed_fragmentcontainer);
        }

        new TransactionDetailFailedPresenter(transactiondetailFailedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transactiondetailfailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transactiondetail_transactiondetail);
    }
}
