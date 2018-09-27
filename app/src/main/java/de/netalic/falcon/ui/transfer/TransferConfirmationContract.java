package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransferConfirmationContract {

    interface View extends BaseView<Presenter>{

        void navigationToCompletedTransfer(Transaction transaction);
        void showErrorTransferNotFound404();
        void showErrorTryingToFinalizeSomeoneElseTransaction404();
        void shoeErrorFinalizingTransactionWithStatusOfSucceed604();
        void shoeErrorFinalizingTransactionWithStatusOfFailed604();
        void showError600();
        void showError401();

    }

    interface Presenter extends BasePresenter{

        void finalizeTransfer(int transactionId);


    }
}
