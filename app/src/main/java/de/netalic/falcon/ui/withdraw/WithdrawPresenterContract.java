package de.netalic.falcon.ui.withdraw;

import java.util.List;

import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface WithdrawPresenterContract {

    interface View extends BaseView<Presenter>{

        void setListWallet(List<Wallet> walletList);


    }

    interface Presenter extends BasePresenter {


        void getWalletList();

    }
}
