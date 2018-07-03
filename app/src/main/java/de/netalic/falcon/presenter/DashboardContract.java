package de.netalic.falcon.presenter;

import de.netalic.falcon.model.Currency;
import de.netalic.falcon.view.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter>{

        void setEmail();
        void setPhoneNumber();
        void errorForNullCurrency();
        void updateExchangeRateCurrency(double rate);

    }

    interface Presenter extends BasePresenter{

        void exchangeRate(Currency currency);

    }
}
