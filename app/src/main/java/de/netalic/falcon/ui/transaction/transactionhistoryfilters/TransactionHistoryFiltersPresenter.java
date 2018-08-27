package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionHistoryFiltersPresenter implements TransactionHistoryFiltersContract.Presenter {

    private TransactionHistoryFiltersContract.View mTransactionFiltersView;


    public TransactionHistoryFiltersPresenter(TransactionHistoryFiltersContract.View transactionFiltersView) {

        mTransactionFiltersView = checkNotNull(transactionFiltersView);
        mTransactionFiltersView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
