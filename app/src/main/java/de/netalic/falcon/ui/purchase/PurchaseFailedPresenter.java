package de.netalic.falcon.ui.purchase;

public class PurchaseFailedPresenter implements PurchaseFailedContract.Presenter {

    private  PurchaseFailedContract.View mPurchaseFailedView;

    public PurchaseFailedPresenter(PurchaseFailedContract.View purchaseFailedView) {
        mPurchaseFailedView = purchaseFailedView;
        mPurchaseFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
