package de.netalic.falcon.presenter;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;

public interface PhoneConfirmationContract {

    interface View extends BaseView<PhoneConfirmationContract.Presenter> {

        void showActivationCodeError(String error);

    }

    interface Presenter extends BasePresenter {

        void resendActivationCode();
        void bind(User user);
    }
}
