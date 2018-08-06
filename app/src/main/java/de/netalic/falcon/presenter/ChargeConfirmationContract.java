package de.netalic.falcon.presenter;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.view.BaseView;

public interface ChargeConfirmationContract {

    interface View extends BaseView<Presenter> {

        void navigationToChargeCompleted(Deposit deposit);
        void navigationToChargeFailed(Deposit deposit);
        void showErrorInvalidWalletId();
        void showErrorWalletNotFound();
        void showErrorDepositNotFound();
        void showErrorInvalidDepositId();
        void showErrorDepositAlreadySucceed();
        void showErrorInvalidBraintreeNonce();

    }

    interface Presenter extends BasePresenter {

        void finalize(int walletId, int depositId,String braintreeNonce);


    }
}
