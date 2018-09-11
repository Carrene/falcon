package de.netalic.falcon.ui.purchase;

public class PurchaseActivityPresenter implements PurchaseConfirmationContract.Presenter {

    private PurchaseConfirmationContract.View mPurchaseConfirmationView;

    public PurchaseActivityPresenter(PurchaseConfirmationContract.View purchaseConfirmationView) {
        mPurchaseConfirmationView = purchaseConfirmationView;
        mPurchaseConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
