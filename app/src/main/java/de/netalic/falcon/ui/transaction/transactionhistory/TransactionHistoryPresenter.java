package de.netalic.falcon.ui.transaction.transactionhistory;

import de.netalic.falcon.data.repository.deposit.DepositRepository;

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
    public void getDepositList() {

        mTransactionHistoryView.showProgressBar();

        DepositRepository.getInstance().getAll(deal -> {

            if (deal.getThrowable()!=null){

                mTransactionHistoryView.dismissProgressBar();
            }

            else {


                switch (deal.getResponse().code()){


                    case 200:{

                        mTransactionHistoryView.setDepositList(deal.getResponse().body());

                        break;
                    }
                }
            }

        });
    }
}
