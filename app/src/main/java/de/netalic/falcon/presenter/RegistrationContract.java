package de.netalic.falcon.presenter;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;

public interface RegistrationContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void claim(User user);
    }
}
