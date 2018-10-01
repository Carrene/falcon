package de.netalic.falcon.ui.charge;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ChargeAmountContract {


    interface View extends BaseView<Presenter> {

        void showChargePaymentConfirmation(Transaction transaction);

        void showErrorInvalidAmount();

        void showErrorInvalidWalletId();

        void showErrorAmountIsSmallerThanLowerBound();

        void showErrorAmountIsGreaterThanUpperBound();

        void showErrorInvalidCurrency();

        void showErrorRatesDoesNotExists();

        void showErrorChargeIsUnAvailable();

        void showErrorVerifyRateIsOutdatedOrItHasWrongCurrency();

        void showErrorVerifyRateIdMissing();

        void showErrorInvalidVerifyRateId();

        void showErrorStartATransferAsAnAnonymous();

        void updateExchangeRateCurrency(Rate rate);

    }

    interface Presenter extends BasePresenter {

        void getWalletList();

        void charge(int id, double amount,int verifyRateId);

        void exchangeRate(Rate rate);

    }

}
