package de.netalic.falcon.ui.setting.recoveryemail;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface SettingRecoveryEmailContract {

    interface View extends BaseView<Presenter>{

        void navigateToDashboard();
        void showErrorInvalidEmail();
        void showErrorEmailAlreadyExists();
    }

    interface Presenter extends BasePresenter{


        void updateEmail(User user);

    }
}
