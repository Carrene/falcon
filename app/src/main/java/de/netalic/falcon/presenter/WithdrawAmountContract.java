package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Rate;
import de.netalic.falcon.view.BaseView;

public interface WithdrawAmountContract {

    interface View extends BaseView<WithdrawAmountContract.Presenter>{

        void updateExchangeRateCurrency(Rate rate);
        void showErrorInvalidCurrency();
        void showErrorRatesDoesNotExists();
        void setRateList(List<Rate> rateList);

    }

    interface Presenter extends BasePresenter{

        void exchangeRate(Rate rate);
        void listExchangeRate();



    }
}
