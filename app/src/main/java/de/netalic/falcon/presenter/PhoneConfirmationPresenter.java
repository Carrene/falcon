package de.netalic.falcon.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.user.UserRepository;
import de.netalic.falcon.view.AuthenticationDefinitionActivity;

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

        UserRepository.getInstance().claim(user, deal -> {

            if (deal.getThrowable() != null) {

                mPhoneConfirmationView.disMissShowProgressBar();
                mPhoneConfirmationView.showActivationCodeError(String.valueOf(deal.getResponse().code()));
            } else {

                switch (deal.getResponse().code()) {

                    case 200:
                        mPhoneConfirmationView.disMissShowProgressBar();
                        mPhoneConfirmationView.showResendCodeAgain();
                }
            }


        });

    }

    @Override
    public void bind(User user) {

        mPhoneConfirmationView.showProgressBar();

        UserRepository.getInstance().bind(user, deal -> {

            if (deal.getResponse().code() == 200) {

                mPhoneConfirmationView.disMissShowProgressBar();
                mPhoneConfirmationView.navigateToRecoveryEmail(user);

            } else {
                mPhoneConfirmationView.disMissShowProgressBar();
                mPhoneConfirmationView.showActivationCodeError(String.valueOf(deal.getResponse().code()));
            }
        });
    }
}
