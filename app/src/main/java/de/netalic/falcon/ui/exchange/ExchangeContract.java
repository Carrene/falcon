package de.netalic.falcon.ui.exchange;

import java.util.List;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ExchangeContract {

    interface View extends BaseView<Presenter>{
        void navigationToAddWallet();
        void setRateList(List<Rate> rateList);
    }

    interface Presenter extends BasePresenter{
        void transfer();

        void listExchangeRate();
    }
}
