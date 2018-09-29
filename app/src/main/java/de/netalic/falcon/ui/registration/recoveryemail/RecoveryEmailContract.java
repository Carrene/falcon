package de.netalic.falcon.ui.registration.recoveryemail;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface RecoveryEmailContract {

    interface View extends BaseView<RecoveryEmailContract.Presenter> {

        void navigateToAuthenticationDefinitionActivity();
        void showErrorInvalidEmail();
        void showErrorEmailAlreadyExists();

    }

    interface Presenter extends BasePresenter {

        void set(User user);

    }
}

