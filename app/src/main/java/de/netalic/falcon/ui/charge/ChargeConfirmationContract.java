package de.netalic.falcon.ui.charge;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

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
