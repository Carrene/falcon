package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.view.BaseView;

public interface WithdrawPresenterContract {

    interface View extends BaseView<Presenter>{

        void setListWallet(List<Wallet> walletList);


    }

    interface Presenter extends BasePresenter{


        void getWalletList();

    }
}
