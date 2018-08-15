package de.netalic.falcon.presenter;

import static com.google.common.base.Preconditions.checkNotNull;
public class TransactionHistoryPresenter implements TransactionHistoryContract.Presenter {

    private TransactionHistoryContract.View mTransactionHistoryView;

    public TransactionHistoryPresenter(TransactionHistoryContract.View transactionHistoryView) {
        mTransactionHistoryView = checkNotNull(transactionHistoryView);
        mTransactionHistoryView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
