package de.netalic.falcon.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import de.netalic.falcon.view.SignInFragment;

import static com.google.common.base.Preconditions.checkNotNull;

public class SignInPresenter implements SignInContract.Presenter {

    @NonNull
    private final SignInContract.View mSignInView;

    public SignInPresenter(@NonNull SignInContract.View signInView) {

        mSignInView=checkNotNull(signInView);
        mSignInView.setPresenter(this);
    }



    @Override
    public void start(Context context) {

    }
}
