package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecoveryEmailPresenter implements RecoveryEmailContract.Presenter {

    @NonNull
    private final RecoveryEmailContract.View mRecoveryEmailView;

    public RecoveryEmailPresenter(@NonNull RecoveryEmailContract.View recoveryEmailView) {

        mRecoveryEmailView = checkNotNull(recoveryEmailView);
        mRecoveryEmailView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}