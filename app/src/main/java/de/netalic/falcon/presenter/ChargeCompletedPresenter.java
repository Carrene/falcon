package de.netalic.falcon.presenter;

public class ChargeCompletedPresenter implements ChargeCompletedContract.Presenter {

    private final ChargeCompletedContract.View mChargeCompletedView;

    public ChargeCompletedPresenter(ChargeCompletedContract.View chargeCompletedView) {
        this.mChargeCompletedView = chargeCompletedView;
    }


    @Override
    public void start() {

    }
}
