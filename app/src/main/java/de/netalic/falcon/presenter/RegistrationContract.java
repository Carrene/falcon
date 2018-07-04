package de.netalic.falcon.presenter;

import android.app.ProgressDialog;
import android.widget.ProgressBar;

import de.netalic.falcon.model.User;
import de.netalic.falcon.view.BaseView;

public interface RegistrationContract {

    interface View extends BaseView<Presenter> {

        void navigationToPhoneConfirmation(User user);
        void errorForNullPhoneNumber();
        void showProgressBar();
        void disMissShowProgressBar();

    }

    interface Presenter extends BasePresenter {
        void claim(User user);
    }
}
