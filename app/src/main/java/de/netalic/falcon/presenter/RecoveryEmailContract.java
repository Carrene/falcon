package de.netalic.falcon.presenter;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;

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

