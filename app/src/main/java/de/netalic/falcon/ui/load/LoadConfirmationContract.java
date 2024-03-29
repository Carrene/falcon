package de.netalic.falcon.ui.load;

import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface LoadConfirmationContract {

    interface View extends BaseView<Presenter> {

        void navigationToChargeCompleted(Receipt receipt);

        void navigationToChargeFailed(Receipt receipt);

        void showErrorBraintreeNonceIsMissing();

        void showErrorCannotFinalizeFailedTransaction();

        void showErrorTransactionNotFound();

        void showErrorInvalidTransactionId();

        void showErrorFinalizeTransferAsAnAnonymous();

        void showErrorWhenFailed();

    }

    interface Presenter extends BasePresenter {

        void finalizeCharge(int transactionId, String braintreeNonce);
        void getUser();
    }
}
