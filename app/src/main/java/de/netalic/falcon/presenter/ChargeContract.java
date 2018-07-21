package de.netalic.falcon.presenter;

import com.google.gson.JsonObject;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.view.BaseView;

public interface ChargeContract {


    interface View extends BaseView<Presenter> {

        void showProgressBar();

        void dismissProgressBar();

        void setListWallet(List<Wallet> walletList);

        void setToken(JsonObject token);

        void showErrorInvalidAmount();

        void showErrorInvalidWalletId();

        void showErrorAmountIsSmallerThanLowerBound();

        void showErrorAmountIsGreaterThanUpperBound();


    }

    interface Presenter extends BasePresenter {

        void getWalletList();

        void charge(int id, double amount);

        void finalize(double amount, String braintreeNonce, String chargeDataToken);

    }

}
