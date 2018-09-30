package de.netalic.falcon.ui.setting.basic;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface SettingContract {

    interface View extends BaseView<Presenter>{


        void setMethodType(String password);

        void setRecoveryEmailState(String email);
    }

    interface Presenter extends BasePresenter{


        void loginMethod();

        void recoveryEmail(int id);
    }
}
