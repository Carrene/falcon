package de.netalic.falcon.presenter;

import com.google.gson.JsonObject;

import java.util.List;

import de.netalic.falcon.model.ChargeStartResponse;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.view.BaseView;

public interface ChargeAmountContract {


    interface View extends BaseView<Presenter> {

        void showChargePaymentConfirmation(ChargeStartResponse chargeStartResponse);

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

        void finalize(double amount, String braintreeNonce, String chargeDataToken);

        void exchangeRate(Rate rate);

    }

}
