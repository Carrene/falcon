package de.netalic.falcon.ui.charge;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ChargeContract {

    interface View extends BaseView<ChargeContract.Presenter> {
        void setListWallet(List<Wallet> walletList);


    }

    interface Presenter extends BasePresenter {
        void getWalletList();
    }
}

