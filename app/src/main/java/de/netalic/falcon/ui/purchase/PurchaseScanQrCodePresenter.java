package de.netalic.falcon.ui.purchase;

public class PurchaseScanQrCodePresenter implements PurchaseScanQrCodeContract.Presenter {

    private PurchaseScanQrCodeContract.View  mPurchaseScanQrCodeView;


    public PurchaseScanQrCodePresenter(PurchaseScanQrCodeContract.View purchaseScanQrCodeView) {

        mPurchaseScanQrCodeView = purchaseScanQrCodeView;
        mPurchaseScanQrCodeView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
