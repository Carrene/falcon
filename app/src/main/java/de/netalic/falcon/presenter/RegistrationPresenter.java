package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

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

                    if (deal.getResponse().code() == 200) {
                        mRegistrationView.navigationToPhoneConfirmation(user);

                    }
                    if (deal.getThrowable() != null) {

                        mRegistrationView.errorForNullPhoneNumber();
                    }

                });
    }
    @Override
    public void start() {

    }

}
