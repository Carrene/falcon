package de.netalic.falcon.ui.charge;

import java.util.List;

import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ListCurrencyContract {

    interface View extends BaseView<Presenter> {

        void setCurrencyList(List<Currency> currencyList);

    }

    interface Presenter extends BasePresenter {

        void getCurrencyList();

    }
}
