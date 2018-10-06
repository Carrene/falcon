package de.netalic.falcon.ui.transaction.transactionhistory;

public class TransactionDetailCompletedPresenter implements TransactionDetailCompletedContract.Presenter {

    private TransactionDetailCompletedContract.View mTransactionDetailCompletedView;


    public TransactionDetailCompletedPresenter(TransactionDetailCompletedContract.View transactionDetailCompletedView) {
        mTransactionDetailCompletedView = transactionDetailCompletedView;
        mTransactionDetailCompletedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
