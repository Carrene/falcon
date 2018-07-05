package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class SplashPresenter implements SplashContract.Presenter {

    @NonNull
    private final SplashContract.View mSignInView;

    public SplashPresenter(@NonNull SplashContract.View signInView) {

        mSignInView=checkNotNull(signInView);
        mSignInView.setPresenter(this);
    }



    @Override
    public void start() {

    }
}
