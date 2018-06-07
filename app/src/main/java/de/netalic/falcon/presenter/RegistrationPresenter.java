package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import de.netalic.falcon.model.User;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.user.UserRepository;
import de.netalic.falcon.repository.user.UserSource;
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
    public void claim(User user) {

//        UserRepository.getInstance().claim(user).promise().then(new DoneCallback<Deal<User, UserSource>>() {
//            @Override
//            public void onDone(Deal<User, UserSource> result) {
//
//            }
//        }).fail(new FailCallback<Throwable>() {
//            @Override
//            public void onFail(Throwable result) {
//
//            }
//        });
    }

    @Override
    public void start() {

    }
}
