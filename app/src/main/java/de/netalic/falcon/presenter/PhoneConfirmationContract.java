package de.netalic.falcon.presenter;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;

public interface PhoneConfirmationContract {

    interface View extends BaseView<PhoneConfirmationContract.Presenter> {

        void showActivationCodeError(String error);
        void navigateToRecoveryEmail(User user);
        void showResendCodeAgain();

    }

    interface Presenter extends BasePresenter {

        void resendActivationCode(User user);
        void bind(User user);
    }
}
