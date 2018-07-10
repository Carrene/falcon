package de.netalic.falcon.presenter;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.view.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter>{

        void setEmail();
        void setPhoneNumber();
        void showErrorInvalidCurrency();
        void showErrorRatesDoesNotExists();
        void updateExchangeRateCurrency(String rate);
        void showProgressBar();
        void dismissShowProgressBar();

    }

    interface Presenter extends BasePresenter{

        void exchangeRate(Currency currency);

    }
}
