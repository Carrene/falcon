package de.netalic.falcon.presenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class QrCodeFailedPresenter implements QrCodeFailedContract.Presenter {

    private QrCodeFailedContract.View mQrCodeFailedView;

    public QrCodeFailedPresenter(QrCodeFailedContract.View qrCodeFailedView) {
        mQrCodeFailedView = checkNotNull(qrCodeFailedView);
        mQrCodeFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
