package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.user.UserRepository;
import nuesoft.helpdroid.validation.Validator;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationPresenter implements RegistrationContract.Presenter {

    @NonNull
    private final RegistrationContract.View mRegistrationView;

    public RegistrationPresenter(@NonNull RegistrationContract.View registrationView) {

        mRegistrationView = checkNotNull(registrationView);
        mRegistrationView.setPresenter(this);
    }

    @Override
    public void register(User user) {

        if (!Validator.isPhoneValid(user.getPhone())) {
            mRegistrationView.showPhoneNumberFormatError();
            return;
        }
        UserRepository.getInstance().login(user);
    }

    @Override
    public void start() {

    }
}
