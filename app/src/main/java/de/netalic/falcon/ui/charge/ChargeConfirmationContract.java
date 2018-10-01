package de.netalic.falcon.ui.charge;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ChargeConfirmationContract {

    interface View extends BaseView<Presenter> {

        void navigationToChargeCompleted(Transaction transaction);
        void navigationToChargeFailed(Transaction transaction);
        void showErrorInvalidWalletId();
        void showErrorWalletNotFound();
        void showErrorDepositNotFound();
        void showErrorInvalidDepositId();
        void showErrorDepositAlreadySucceed();
        void showErrorInvalidBraintreeNonce();

    }

    interface Presenter extends BasePresenter {

        void finalizeCharge(int transactionId,String braintreeNonce);


    }
}
