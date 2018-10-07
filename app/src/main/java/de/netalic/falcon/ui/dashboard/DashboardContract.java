package de.netalic.falcon.ui.dashboard;

import java.util.List;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter>{

        void setListWallet(List<Wallet>walletList);
        void setListRates(List<Rate> listRates);
        void setBaseCurrency(String currency);
        void setBaseCurrencyNotSet();
    }

    interface Presenter extends BasePresenter {

        void getWalletList();
        void getListRates();
        void baseCurrency();

    }
}
