package de.netalic.falcon.ui.registration.phoneinput;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneInputPresenter implements PhoneInputContract.Presenter {


    @NonNull
    private final PhoneInputContract.View mPhoneInputView;

    PhoneInputPresenter(@NonNull PhoneInputContract.View registrationView) {

        mPhoneInputView = checkNotNull(registrationView);
        mPhoneInputView.setPresenter(this);
    }

    @Override
    public void claim(User user) {

        mPhoneInputView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(UserRepository.class).claim(user, deal -> {
            if (deal.getThrowable() != null) {

                mPhoneInputView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {
                    case 200:

                        mPhoneInputView.navigationToPhoneConfirmation(user);
                        break;

                    case 710: {

                        mPhoneInputView.showErrorInvalidUdidOrPhone();
                        break;
                    }
                }

            }
            mPhoneInputView.dismissProgressBar();
        });
    }


    @Override
    public void start() {


    }
}
