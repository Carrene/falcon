package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransferConfirmationContract {

    interface View extends BaseView<Presenter>{

        void navigationToCompletedTransfer(Transaction transaction);
        void navigationToTransferFailed();
        void showResponseCodeInvalidSourceWalletAddress();
        void showResponseCodeInvalidDestinationWalletAddress();
        void showResponseCodeSourceWalletNotFound();
        void showResponseCodeSourceAndDestinationIsSame();
        void showResponseCodeInvalidAmount();
        void showResponseCodeAmountIsNegative();
        void showResponseInsufficientBalance();
        void showResponseTryingToTransferFromAnotherClientWallet();


    }

    interface Presenter extends BasePresenter{

        void finalizeTransfer(int transactionId);


    }
}
