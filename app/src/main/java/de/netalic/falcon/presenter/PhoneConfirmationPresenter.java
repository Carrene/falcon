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

        UserRepository.getInstance().claim(user,deal -> {


            if (deal.getResponse().code() == 200) {
                mPhoneConfirmationView.showResendCodeAgain();

            }
            if (deal.getThrowable() != null) {

                mPhoneConfirmationView.showActivationCodeError(String.valueOf(deal.getResponse().code()));
            }
        });

    }

    @Override
    public void bind(User user) {
        UserRepository.getInstance().bind(user, deal -> {


                if (deal.getResponse().code() == 200) {

                    mPhoneConfirmationView.navigateToRecoveryEmail(user);

                } else {
                    mPhoneConfirmationView.showActivationCodeError(String.valueOf(deal.getResponse().code()));
                }
            });


    }
}
