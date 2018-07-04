package de.netalic.falcon.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class AuthenticationDefinitionPresenter implements AuthenticationDefinitionContract.Presenter {

    @NonNull
    private final AuthenticationDefinitionContract.View mAuthenticationDefinitionView;

    public AuthenticationDefinitionPresenter(@NonNull AuthenticationDefinitionContract.View authenticationDefinitionView) {

        mAuthenticationDefinitionView = checkNotNull(authenticationDefinitionView);
        mAuthenticationDefinitionView.setPresenter(this);
    }


    @Override
    public void start(Context context) {

    }
}
