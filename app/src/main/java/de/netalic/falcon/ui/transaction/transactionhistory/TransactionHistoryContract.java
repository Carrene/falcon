package de.netalic.falcon.ui.transaction.transactionhistory;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransactionHistoryContract {


    interface View extends BaseView<Presenter> {

        void setDepositList(List<Deposit> depositList);
    }

    interface Presenter extends BasePresenter {

        void getDepositList(Map<String, ?> filterMap, int take, int skip);
    }
}
