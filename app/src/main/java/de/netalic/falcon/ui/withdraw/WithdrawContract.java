package de.netalic.falcon.ui.withdraw;

import java.util.List;

import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface WithdrawContract {

    interface View extends BaseView<Presenter>{

        void setListWallet(List<Wallet> walletList);
        void internetConnectionError();


    }

    interface Presenter extends BasePresenter {


        void getWalletList();

    }
}
