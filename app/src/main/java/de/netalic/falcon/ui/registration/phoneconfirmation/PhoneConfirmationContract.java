package de.netalic.falcon.ui.registration.phoneconfirmation;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;


public interface PhoneConfirmationContract {

    interface View extends BaseView<PhoneConfirmationContract.Presenter> {

        void showErrorInvalidUdidOrPhone();

        void showErrorInvalidActivationCode();

        void showErrorInvalidDeviceName();

        void navigateToRecoveryEmail(User user);

        void showResendCodeAgain();


    }

    interface Presenter extends BasePresenter {

        void resendActivationCode(User user);

        void bind(User user);
    }
}
