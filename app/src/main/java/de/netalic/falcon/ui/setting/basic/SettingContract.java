package de.netalic.falcon.ui.setting.basic;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface SettingContract {

    interface View extends BaseView<Presenter>{


        void setPatternType();

        void setPasswordType();

        void setRecoveryEmailState(String email);

        void setEmailNotSet();

        void setPhoneNumber(String phone);

        void setPhoneNumberNotSet();

        void setBaseCurrency(String currency);

        void setBaseCurrencyNotSet();

    }

    interface Presenter extends BasePresenter{


        void loginMethod();

        void recoveryEmail(String email);

        void phoneNumber(String phone);

        void baseCurrency();
    }
}
