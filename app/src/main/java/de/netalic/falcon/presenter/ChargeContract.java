package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.view.BaseView;

public interface ChargeContract {


    interface View extends BaseView<Presenter>{

        void showProgressBar();
        void dismissProgressBar();
        void setListWallet(List<Wallet> walletList);


    }

    interface Presenter extends BasePresenter{

        void getWalletList();
        void getToken(int id,double amount);

    }

}
