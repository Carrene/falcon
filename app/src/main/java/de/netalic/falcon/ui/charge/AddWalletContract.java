package de.netalic.falcon.ui.charge;

import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface AddWalletContract {

    interface View extends BaseView<Presenter>{


        void setWallet(Wallet wallet);
        void errorWalletNameAlreadyExist();
        void errorWalletWithThisCurrencyAlreadyExist();
        void errorInvalidCurrencyCode();
        void errorInvalidWalletName();
        void errorAddWalletAsAnAnonymous();

    }

    interface Presenter extends BasePresenter {


        void addWallet(String walletName,String walletCurrencyCode);

    }
}
