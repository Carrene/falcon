package de.netalic.falcon.ui.authentication.registration;

import de.netalic.falcon.model.User;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface RegistrationContract {

    interface View extends BaseView<Presenter> {

        void navigationToPhoneConfirmation(User user);
        void showErrorInvalidUdidOrPhone();

    }

    interface Presenter extends BasePresenter {
        void claim(User user);
    }
}
