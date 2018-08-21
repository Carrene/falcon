package de.netalic.falcon.ui.withdraw;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawQrCodeFailedPresenter implements WithdrawQrCodeFailedContract.Presenter {

    private WithdrawQrCodeFailedContract.View mQrCodeFailedView;

    public WithdrawQrCodeFailedPresenter(WithdrawQrCodeFailedContract.View qrCodeFailedView) {
        mQrCodeFailedView = checkNotNull(qrCodeFailedView);
        mQrCodeFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
