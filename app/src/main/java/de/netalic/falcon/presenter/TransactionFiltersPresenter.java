package de.netalic.falcon.presenter;

import static com.google.common.base.Preconditions.checkNotNull;
public class TransactionFiltersPresenter implements TransactionFiltersContract.Presenter {

    private TransactionFiltersContract.View mTransactionFiltersView;


    public TransactionFiltersPresenter(TransactionFiltersContract.View transactionFiltersView) {
        mTransactionFiltersView =checkNotNull(transactionFiltersView);
        mTransactionFiltersView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
