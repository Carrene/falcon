package de.netalic.falcon.ui.charge;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ChargeConfirmationContract {

    interface View extends BaseView<Presenter> {

        void navigationToChargeCompleted(Transaction transaction);
        void showErrorBraintreeNonceIsMissing();
        void showErrorCannotFinalizeFailedTransaction();
        void showErrorTransactionNotFound();
        void showErrorInvalidTransactionId();
        void showErrorFinalizeTransferAsAnAnonymous();

    }

    interface Presenter extends BasePresenter {

        void finalizeCharge(int transactionId,String braintreeNonce);


    }
}
