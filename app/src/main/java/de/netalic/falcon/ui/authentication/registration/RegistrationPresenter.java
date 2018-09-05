package de.netalic.falcon.ui.authentication.registration;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;

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

        mRegistrationView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(UserRepository.class).claim(user, deal -> {
            if (deal.getThrowable() != null) {

                mRegistrationView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {
                    case 200:

                        mRegistrationView.navigationToPhoneConfirmation(user);
                        break;

                    case 710: {

                        mRegistrationView.showErrorInvalidUdidOrPhone();
                        break;
                    }
                }
                mRegistrationView.dismissProgressBar();
            }
        });
    }


    @Override
    public void start() {


    }
}
