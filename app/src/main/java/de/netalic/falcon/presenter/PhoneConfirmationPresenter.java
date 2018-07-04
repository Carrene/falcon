package de.netalic.falcon.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.user.UserRepository;
import de.netalic.falcon.view.AuthenticationDefinitionActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneConfirmationPresenter implements PhoneConfirmationContract.Presenter {

    private Context mContext;

    @NonNull
    private final PhoneConfirmationContract.View mPhoneConfirmationView;

    public PhoneConfirmationPresenter(@NonNull PhoneConfirmationContract.View phoneConfirmationView) {

        mPhoneConfirmationView = checkNotNull(phoneConfirmationView);
        mPhoneConfirmationView.setPresenter(this);
    }

    @Override
    public void start(Context context) {

        mContext=context;
    }

    @Override
    public void resendActivationCode(User user) {

        mPhoneConfirmationView.showProgressBar();

        UserRepository.getInstance().claim(user, deal -> {

            if (deal.getThrowable() != null) {

                mPhoneConfirmationView.disMissShowProgressBar();

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

            if (deal.getThrowable()!=null){

                mPhoneConfirmationView.disMissShowProgressBar();
            }

            else {

                switch (deal.getResponse().code()){

                    case 200:
                        mPhoneConfirmationView.disMissShowProgressBar();
                        mPhoneConfirmationView.navigateToRecoveryEmail(user);
                    case 711:
                        mPhoneConfirmationView.disMissShowProgressBar();
                        mPhoneConfirmationView.showActivationCodeError(mContext.getString(R.string.phoneconfirmation_invalidactivationcode));

                }
            }

        });
    }



}
