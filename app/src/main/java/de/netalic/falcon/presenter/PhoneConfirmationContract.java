package de.netalic.falcon.presenter;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;


public interface PhoneConfirmationContract {

    interface View extends BaseView<PhoneConfirmationContract.Presenter> {

        void showErrorInvalidUdidOrPhone();

        void showErrorInvalidActivationCode();

        void showErrorInvalidDeviceName();

        void navigateToRecoveryEmail(User user);

        void showResendCodeAgain();

        void showProgressBar();

        void dismissProgressBar();

    }

    interface Presenter extends BasePresenter {

        void resendActivationCode(User user);

        void bind(User user);
    }
}
