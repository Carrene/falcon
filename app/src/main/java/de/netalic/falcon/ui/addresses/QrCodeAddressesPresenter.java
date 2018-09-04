package de.netalic.falcon.ui.addresses;

public class QrCodeAddressesPresenter implements QrCodeAddressesContract.Presenter {

    private QrCodeAddressesContract.View mQrCodeView;


    public QrCodeAddressesPresenter(QrCodeAddressesContract.View qrCodeView) {
        mQrCodeView = qrCodeView;
        mQrCodeView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
