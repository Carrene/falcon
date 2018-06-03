package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

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
}
