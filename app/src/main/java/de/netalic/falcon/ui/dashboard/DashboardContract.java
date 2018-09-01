package de.netalic.falcon.ui.dashboard;

import java.util.List;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter>{

        void showErrorInvalidCurrency();
        void showErrorRatesDoesNotExists();
        void updateExchangeRateCurrency(Rate rate);
        void setListWallet(List<Wallet>walletList);

    }

    interface Presenter extends BasePresenter {

        void exchangeRate(Rate rate);
        void getWalletList();

    }
}
