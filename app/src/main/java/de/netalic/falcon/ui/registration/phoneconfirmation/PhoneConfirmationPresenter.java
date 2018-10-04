package de.netalic.falcon.ui.registration.phoneconfirmation;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneConfirmationPresenter implements PhoneConfirmationContract.Presenter {


    @NonNull
    private final PhoneConfirmationContract.View mPhoneConfirmationView;

    public PhoneConfirmationPresenter(@NonNull PhoneConfirmationContract.View phoneConfirmationView) {

        mPhoneConfirmationView = checkNotNull(phoneConfirmationView);
        mPhoneConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {


    }

    @Override
    public void resendActivationCode(User user) {

        mPhoneConfirmationView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(UserRepository.class).claim(user, deal -> {

            if (deal.getThrowable() != null) {

                mPhoneConfirmationView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mPhoneConfirmationView.showResendCodeAgain();
                        break;
                    }

                    case 710: {

                        mPhoneConfirmationView.showErrorInvalidUdidOrPhone();
                        break;
                    }


                }

            }
            mPhoneConfirmationView.dismissProgressBar();

        });

    }

    @Override
    public void bind(User user) {

        mPhoneConfirmationView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(UserRepository.class).bind(user, deal -> {

            if (deal.getThrowable() != null) {

                mPhoneConfirmationView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {
                        mPhoneConfirmationView.dismissProgressBar();
                        mPhoneConfirmationView.navigateToRecoveryEmail(user);
                        break;
                    }
                    case 710: {
                        mPhoneConfirmationView.dismissProgressBar();
                        mPhoneConfirmationView.showErrorInvalidUdidOrPhone();
                        break;
                    }
                    case 711: {
                        mPhoneConfirmationView.dismissProgressBar();
                        mPhoneConfirmationView.showErrorInvalidActivationCode();
                        break;
                    }
                    case 716: {
                        mPhoneConfirmationView.dismissProgressBar();
                        mPhoneConfirmationView.showErrorInvalidDeviceName();
                        break;
                    }
                }
            }
            mPhoneConfirmationView.dismissProgressBar();
        });
    }
}
