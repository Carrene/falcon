package de.netalic.falcon.ui.exchange;

import java.util.List;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ExchangeContract {

    interface View extends BaseView<Presenter> {
        void navigationToAddWallet();

        void setRateList(List<Rate> rateList);

        void setListWallet(List<Wallet> model);

        void navigationToExchangeConfirmation(Transaction body);

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

        void internetConnectionError();
    }

    interface Presenter extends BasePresenter {
        void transfer(int walletId, String address, float amount);

        void getWalletList();

        void listExchangeRate();
    }
}
