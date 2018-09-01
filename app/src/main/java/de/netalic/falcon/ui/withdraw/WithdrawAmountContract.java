package de.netalic.falcon.ui.withdraw;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface WithdrawAmountContract {

    interface View extends BaseView<WithdrawAmountContract.Presenter> {

        void setRateList(List<Rate> rateList);

        void setQrCode(String qrCodeContent);

    }

    interface Presenter extends BasePresenter {

        void listExchangeRate();

        void generateQrCode(Map<String, Object> map);

    }
}
