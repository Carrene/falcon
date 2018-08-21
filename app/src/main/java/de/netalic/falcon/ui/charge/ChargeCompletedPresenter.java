package de.netalic.falcon.ui.charge;

import static com.google.common.base.Preconditions.checkNotNull;
public class ChargeCompletedPresenter implements ChargeCompletedContract.Presenter {

    private final ChargeCompletedContract.View mChargeCompletedView;

    public ChargeCompletedPresenter(ChargeCompletedContract.View chargeCompletedView) {
        mChargeCompletedView =checkNotNull(chargeCompletedView);
        mChargeCompletedView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
