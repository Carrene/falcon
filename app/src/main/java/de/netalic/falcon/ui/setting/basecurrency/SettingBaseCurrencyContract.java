package de.netalic.falcon.ui.setting.basecurrency;

import java.util.Currency;
import java.util.List;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface SettingBaseCurrencyContract {

    interface View extends BaseView<Presenter> {

        void setCurrencyList(List<de.netalic.falcon.data.model.Currency>currencyList);

    }

    interface Presenter extends BasePresenter {

        void getCurrencyList();

    }
}
