package de.netalic.falcon.ui.withdraw;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawQrCodeCompletedPresenter implements WithdrawQrCodeCompletedContract.Presenter {

    @NonNull
    private WithdrawQrCodeCompletedContract.View mQrCodeCompletedView;


    public WithdrawQrCodeCompletedPresenter(WithdrawQrCodeCompletedContract.View qrCodeCompletedView) {

        mQrCodeCompletedView = checkNotNull(qrCodeCompletedView);
        mQrCodeCompletedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
