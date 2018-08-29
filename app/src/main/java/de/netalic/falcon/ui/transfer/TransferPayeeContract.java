package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransferPayeeContract {

    interface View extends BaseView<Presenter>{

        void showResponseCode();


    }

    interface Presenter extends BasePresenter{

        void transfer(int sourceAddress,double amount,int walletAddress);

    }
}
