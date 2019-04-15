package de.netalic.falcon.common.listcurrency;

import java.util.List;

import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ListCurrencyContract {

    interface View extends BaseView<Presenter> {

        void setCurrencyList(List<Rate> currencyList);

    }

    interface Presenter extends BasePresenter {

        void getCurrencyList();

    }
}
