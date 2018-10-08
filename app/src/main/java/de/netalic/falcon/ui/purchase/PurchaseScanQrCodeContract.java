package de.netalic.falcon.ui.purchase;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface PurchaseScanQrCodeContract {

    interface View extends BaseView<Presenter>{

        void navigationToTransferConfirmation(Transaction transaction);
        void showError700();
        void showError727();
        void showErrorSourceWalletNotFound404();
        void showError601();
        void showErrorInvalidAmount702();
        void showErrorAmountIsZero702();
        void showErrorAmountIsNegative702();
        void showError600();
        void showErrorTryingToTransferFromOtherWallet404();
        void showError602();
        void showError401();

    }

    interface Presenter extends BasePresenter {

        void startTransfer(int sourceWalletId,String destinationWalletId,float amount);

    }
}
