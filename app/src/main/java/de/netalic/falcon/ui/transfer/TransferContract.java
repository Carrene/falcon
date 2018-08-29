package de.netalic.falcon.ui.transfer;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransferContract {

    interface View extends BaseView<Presenter> {

        void setWalletList(List<Wallet>walletList);

    }

    interface Presenter extends BasePresenter {

        void getWalletList();

    }
}
