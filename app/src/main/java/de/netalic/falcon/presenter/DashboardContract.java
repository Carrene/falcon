package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.view.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter>{

        void showErrorInvalidCurrency();
        void showErrorRatesDoesNotExists();
        void updateExchangeRateCurrency(Rate rate);
        void setListWallet(List<Wallet>walletList);

    }

    interface Presenter extends BasePresenter{

        void exchangeRate(Rate rate);
        void getWalletList();

    }
}
