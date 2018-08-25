package de.netalic.falcon.ui.transaction.transactionhistory;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.repository.base.Deal;
import de.netalic.falcon.data.repository.base.IRepository;
import de.netalic.falcon.data.repository.deposit.DepositRepository;
import de.netalic.falcon.model.Deposit;

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

    @Override
    public void getDepositList(Map<String, ?> filterMap, int take, int skip) {

//        mTransactionHistoryView.showProgressBar();
        DepositRepository.getInstance().getAll(deal -> {
            if (deal.getThrowable() != null) {

//                mTransactionHistoryView.dismissProgressBar();
            } else {


                switch (deal.getResponse().code()) {


                    case 200: {

                        mTransactionHistoryView.setDepositList(deal.getResponse().body());

                        break;
                    }
                }
            }
        }, filterMap, take, skip);

    }
}
