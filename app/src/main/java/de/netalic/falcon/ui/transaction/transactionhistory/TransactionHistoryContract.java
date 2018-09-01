package de.netalic.falcon.ui.transaction.transactionhistory;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Deposit;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransactionHistoryContract {


    interface View extends BaseView<Presenter> {

        void setDepositList(List<Deposit> depositList);

        void showPaginationError(boolean show);

        void showPaginationLoading(boolean loading);

        void loadNoMoreItem(boolean stop);
    }

    interface Presenter extends BasePresenter {

        void getDepositList(Map<String, ?> filterMap);

        void resetPagination();
    }
}
