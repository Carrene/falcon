package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.user.UserRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationPresenter implements RegistrationContract.Presenter {

    @NonNull
    private final RegistrationContract.View mRegistrationView;

    public RegistrationPresenter(@NonNull RegistrationContract.View registrationView) {

        mRegistrationView = checkNotNull(registrationView);
        mRegistrationView.setPresenter(this);
    }

    @Override
    public void claim(User user) {

        UserRepository.getInstance().claim(user, deal -> {

            if (deal.getThrowable() != null) {

                mRegistrationView.errorForNullPhoneNumber();

            } else {
                switch (deal.getResponse().code()) {
                    case 200: {
                        mRegistrationView.navigationToPhoneConfirmation(user);

                     }
                }
            }

        });
    }

    @Override
    public void start() {

    }

}
