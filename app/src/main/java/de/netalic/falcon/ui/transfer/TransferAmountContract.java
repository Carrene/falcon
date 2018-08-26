package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransferAmountContract {

    interface View extends BaseView<Presenter>{

        void showErrorInvalidCurrency();

        void showErrorRatesDoesNotExists();

        void updateExchangeRateCurrency(de.netalic.falcon.model.Rate rate);

    }

    interface Presenter  extends BasePresenter{

        void exchangeRate(de.netalic.falcon.model.Rate rate);


    }
}
