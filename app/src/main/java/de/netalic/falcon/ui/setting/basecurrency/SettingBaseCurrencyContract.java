package de.netalic.falcon.ui.setting.basecurrency;

import java.util.List;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface SettingBaseCurrencyContract {

    interface View extends BaseView<Presenter> {

        void setCurrencyList(List<de.netalic.falcon.data.model.Currency>currencyList);

        void setBaseCurrency(User currency);
    }

    interface Presenter extends BasePresenter {

        void getCurrencyList();

        void changeBaseCurrency(int userId,String baseCurrencyCode);

    }
}
