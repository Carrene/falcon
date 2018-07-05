package de.netalic.falcon.presenter;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;

public interface RegistrationContract {

    interface View extends BaseView<Presenter> {

        void navigationToPhoneConfirmation(User user);
        void showProgressBar();
        void dismissProgressBar();
        void showErrorInvalidUdidOrPhone();

    }

    interface Presenter extends BasePresenter {
        void claim(User user);
    }
}
