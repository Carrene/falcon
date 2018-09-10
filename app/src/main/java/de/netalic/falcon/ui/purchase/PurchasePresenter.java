package de.netalic.falcon.ui.purchase;

public class PurchasePresenter implements PurchaseContract.Presenter {

    private PurchaseContract.View mPurchaseView;


    public PurchasePresenter(PurchaseContract.View purchaseView) {
        mPurchaseView = purchaseView;
        mPurchaseView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
