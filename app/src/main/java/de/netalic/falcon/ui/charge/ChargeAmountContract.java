package de.netalic.falcon.ui.charge;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ChargeAmountContract {


    interface View extends BaseView<Presenter> {

        void showChargePaymentConfirmation(Deposit deposit);

        void showErrorInvalidAmount();

        void showErrorInvalidWalletId();

        void showErrorAmountIsSmallerThanLowerBound();

        void showErrorAmountIsGreaterThanUpperBound();

        void showErrorInvalidCurrency();

        void showErrorRatesDoesNotExists();

        void updateExchangeRateCurrency(Rate rate);

    }

    interface Presenter extends BasePresenter {

        void getWalletList();

        void charge(int id, double amount);


        void exchangeRate(Rate rate);

    }

}
