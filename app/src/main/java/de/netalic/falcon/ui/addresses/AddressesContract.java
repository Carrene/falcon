package de.netalic.falcon.ui.addresses;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface AddressesContract {

    interface View extends BaseView<Presenter>{

        void setWalletList(List<Wallet>walletList);

    }

    interface Presenter extends BasePresenter{

        void getWalletList();

    }
}
