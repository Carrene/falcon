package de.netalic.falcon.ui.charge;

import android.support.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFailedPresenter implements ChargeFailedContract.Presenter {


    @NonNull
    private final ChargeFailedContract.View mChargeFailedView;

    public ChargeFailedPresenter(ChargeFailedContract.View chargeFailedView) {

        mChargeFailedView =checkNotNull(chargeFailedView);
        mChargeFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
