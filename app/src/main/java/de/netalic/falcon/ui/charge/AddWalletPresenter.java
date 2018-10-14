package de.netalic.falcon.ui.charge;

public class AddWalletPresenter implements AddWalletContract.Presenter {

    private AddWalletContract.View mView;



    public AddWalletPresenter(AddWalletContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
