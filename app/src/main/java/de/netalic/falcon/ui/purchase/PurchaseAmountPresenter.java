package de.netalic.falcon.ui.purchase;

public class PurchaseAmountPresenter implements PurchaseAmountContract.Presenter {

    private PurchaseAmountContract.View mPurchaseAmountView;


    public PurchaseAmountPresenter(PurchaseAmountContract.View purchaseAmountView) {
        mPurchaseAmountView = purchaseAmountView;
        mPurchaseAmountView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
