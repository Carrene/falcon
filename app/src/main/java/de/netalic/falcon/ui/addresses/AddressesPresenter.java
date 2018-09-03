package de.netalic.falcon.ui.addresses;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

public class AddressesPresenter implements AddressesContract.Presenter {

    private AddressesContract.View mAddressesView;

    public AddressesPresenter(AddressesContract.View addressesView) {
        mAddressesView = addressesView;
        mAddressesView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void getWalletList() {

        mAddressesView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable()!=null){

            }
            else {

                switch (deal.getResponse().code()){

                    case 200:{

                        mAddressesView.setWalletList(deal.getResponse().body());
                        break;
                    }
                }
            }

            mAddressesView.dismissProgressBar();

        });
    }
}
