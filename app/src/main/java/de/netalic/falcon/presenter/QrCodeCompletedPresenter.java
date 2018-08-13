package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class QrCodeCompletedPresenter implements QrCodeCompletedContract.Presenter {

    @NonNull
    private QrCodeCompletedContract.View mQrCodeCompletedView;


    public QrCodeCompletedPresenter(QrCodeCompletedContract.View qrCodeCompletedView) {

        mQrCodeCompletedView = checkNotNull(qrCodeCompletedView);
        mQrCodeCompletedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
