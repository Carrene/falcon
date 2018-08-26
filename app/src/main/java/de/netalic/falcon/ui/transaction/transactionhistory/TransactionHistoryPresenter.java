package de.netalic.falcon.ui.transaction.transactionhistory;

import java.util.Map;

import de.netalic.falcon.data.repository.deposit.DepositRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionHistoryPresenter implements TransactionHistoryContract.Presenter {

    private TransactionHistoryContract.View mTransactionHistoryView;
    private int mTake = 10;
    private int mSkip = 0;

    public TransactionHistoryPresenter(TransactionHistoryContract.View transactionHistoryView) {

        mTransactionHistoryView = checkNotNull(transactionHistoryView);
        mTransactionHistoryView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getDepositList(Map<String, ?> filterMap) {
        //TODO use condition from web service response for continue or not
        mTransactionHistoryView.showPaginationLoading(true);

        DepositRepository.getInstance().getAll(deal -> {
            if (deal.getThrowable() != null) {
                mTransactionHistoryView.showPaginationError(true);
            } else {
                switch (deal.getResponse().code()) {

                    case 200: {
                        mTransactionHistoryView.showPaginationLoading(false);
                        mSkip += mTake;
                        mTransactionHistoryView.setDepositList(deal.getResponse().body());
                        if (mSkip > 25) {
                            mTransactionHistoryView.loadNoMoreItem(true);
                        }
                        break;
                    }
                }
            }
        }, filterMap, mTake, mSkip);
    }
}