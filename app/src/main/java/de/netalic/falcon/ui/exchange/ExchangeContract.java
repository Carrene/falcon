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

        void setWalletRate();

        void setListWallet(List<Wallet> model);

        void navigationToExchangeConfirmation(Transaction body);
    }

    interface Presenter extends BasePresenter {
        void transfer(int walletId, String address, float amount);

        void getWalletList();

        void listExchangeRate();
    }
}
