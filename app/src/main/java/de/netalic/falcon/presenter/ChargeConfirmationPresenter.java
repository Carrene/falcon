package de.netalic.falcon.presenter;

public class ChargeConfirmationPresenter implements ChargeConfirmationContract.Presenter {

    private final ChargeConfirmationContract.View mChargeConfirmationView;

    public ChargeConfirmationPresenter(ChargeConfirmationContract.View chargeConfirmationView) {
        mChargeConfirmationView=chargeConfirmationView;
    }

    @Override
    public void start() {

    }
}
