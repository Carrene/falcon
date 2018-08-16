package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.view.BaseView;

public interface TransactionHistoryContract {


    interface View extends BaseView<Presenter>{

        void setDepositList(List<Deposit>depositList);


    }

    interface Presenter extends BasePresenter{

        void getDepositList();


    }
}
