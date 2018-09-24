package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface TransferPayeeContract {

    interface View extends BaseView<Presenter>{

        void navigationToTransferConfirmation();
    }

    interface Presenter extends BasePresenter{

        void startTransfer(int sourceWalletId,String destinationWalletId,float amount);


    }
}
