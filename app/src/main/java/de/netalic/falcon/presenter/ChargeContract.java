package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.view.BaseView;

public interface ChargeContract {

    interface View extends BaseView<ChargeContract.Presenter> {
        void setListWallet(List<Wallet> walletList);


    }

    interface Presenter extends BasePresenter {
        void getWalletList();
    }
}

