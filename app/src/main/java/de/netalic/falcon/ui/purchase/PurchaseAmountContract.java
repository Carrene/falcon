package de.netalic.falcon.ui.purchase;

import java.util.Map;

import de.netalic.falcon.data.model.Purchase;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface PurchaseAmountContract {

    interface View extends BaseView<Presenter>{

        void setQrCode(String qrCodeContent);

        void showErrorInvalidCurrency();

        void showErrorRatesDoesNotExists();

        void updateExchangeRateCurrency(Rate rate);

    }

    interface Presenter extends BasePresenter{

        void generateQrCode(Purchase purchase);

        void exchangeRate(Rate rate);
    }
}
