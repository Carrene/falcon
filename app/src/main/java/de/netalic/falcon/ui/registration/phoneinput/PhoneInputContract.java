package de.netalic.falcon.ui.registration.phoneinput;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface PhoneInputContract {

    interface View extends BaseView<Presenter> {

        void navigationToPhoneConfirmation(User user);
        void showErrorInvalidUdidOrPhone();

    }

    interface Presenter extends BasePresenter {
        void claim(User user);
    }
}
