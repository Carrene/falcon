package de.netalic.falcon.ui.transaction.transactionhistory;

public class TransactionDetailFailedPresenter implements TransactionDetailFailedContract.Presenter {

    private TransactionDetailFailedContract.View mTransactionDetailFailedView;


    public TransactionDetailFailedPresenter(TransactionDetailFailedContract.View transactionDetailFailedView) {
        mTransactionDetailFailedView = transactionDetailFailedView;
        mTransactionDetailFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
