package de.netalic.falcon.ui.load;

import java.util.List;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface LoadContract {

    interface View extends BaseView<LoadContract.Presenter> {
        void setListWallet(List<Wallet> walletList);
        void setRateList(List<Rate> rateList);
        void updateExchangeRateCurrency(Rate rate);
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

    }

    interface Presenter extends BasePresenter {
        void getWalletList();
        void listExchangeRate();
        void exchangeRate(String codeCurrency);
        void charge(int id, double amount,int verifyRateId);
    }
}

