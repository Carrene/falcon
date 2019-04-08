package de.netalic.falcon.ui.receive;

import java.util.List;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ReceiveContract {

    interface View extends BaseView<Presenter>{
        void setRateList(List<Rate> rateList);
        void internetConnectionError();

    }

    interface Presenter extends BasePresenter{
        void listExchangeRate();

    }
}
