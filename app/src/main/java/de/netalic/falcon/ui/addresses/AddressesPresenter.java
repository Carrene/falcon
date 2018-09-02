package de.netalic.falcon.ui.addresses;

public class AddressesPresenter implements AddressesContract.Presenter {

    private AddressesContract.View mAddressesView;

    public AddressesPresenter(AddressesContract.View addressesView) {
        mAddressesView = addressesView;
        mAddressesView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
