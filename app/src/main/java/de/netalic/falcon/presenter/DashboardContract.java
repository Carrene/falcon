package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.ExchangeRate;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.view.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter>{

        void showErrorInvalidCurrency();
        void showErrorRatesDoesNotExists();
        void updateExchangeRateCurrency(String rate);
        void showProgressBar();
        void dismissProgressBar();
        void showListWallet(List<Wallet>walletList);

    }

    interface Presenter extends BasePresenter{

        void exchangeRate(ExchangeRate exchangeRate);
        void getWalletList();

    }
}
