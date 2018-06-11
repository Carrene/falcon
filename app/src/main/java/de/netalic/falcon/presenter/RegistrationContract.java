package de.netalic.falcon.presenter;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;

public interface RegistrationContract {

    interface View extends BaseView<Presenter> {

        void navigationToPhoneconfirmation(User user);
        void errorForNullmessage();

    }

    interface Presenter extends BasePresenter {
        void claim(User user,String codeCountry,String phoneNumber);
    }
}
