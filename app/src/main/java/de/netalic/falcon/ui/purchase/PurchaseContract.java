package de.netalic.falcon.ui.purchase;

import java.util.List;

import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface PurchaseContract {

    interface View extends BaseView<Presenter>{

        void setListWallet(List<Wallet> walletList);
    }

    interface Presenter extends BasePresenter{

        void getWalletList();

    }
}
