package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.user.UserRepository;

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
    public void resendActivationCode() {

    }

    @Override
    public void bind(User user) {
        UserRepository.getInstance().bind(user, deal -> {

            if (deal.getResponse().code() == 200) {


            } else {
                mPhoneConfirmationView.showActivationCodeError(String.valueOf(deal.getResponse().code()));
            }
        });
    }
}
