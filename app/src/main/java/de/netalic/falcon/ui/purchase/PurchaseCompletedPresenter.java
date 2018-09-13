package de.netalic.falcon.ui.purchase;

public class PurchaseCompletedPresenter implements PurchaseCompletedContract.Presenter {

    private PurchaseCompletedContract.View mPurchaseCompletedView;


    public PurchaseCompletedPresenter(PurchaseCompletedContract.View purchaseCompletedView) {
        mPurchaseCompletedView = purchaseCompletedView;
        mPurchaseCompletedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
