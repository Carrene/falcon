package de.netalic.falcon.ui.charge;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

public class AddWalletPresenter implements AddWalletContract.Presenter {

    private AddWalletContract.View mView;



    public AddWalletPresenter(AddWalletContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void addWallet(String walletName, String walletCurrencyCode) {

        RepositoryLocator.getInstance().getRepository(WalletRepository.class).addWallet(walletName,walletCurrencyCode,deal -> {

            if (deal.getThrowable()==null){

                switch (deal.getResponse().code()){

                    case 200:{

                        mView.setWallet(deal.getModel());
                        break;
                    }
                    case 730:{

                        mView.errorWalletNameAlreadyExist();
                        break;
                    }
                    case 731:{

                        mView.errorWalletWithThisCurrencyAlreadyExist();
                        break;
                    }
                    case 732:{

                        mView.errorInvalidCurrencyCode();
                        break;
                    }
                    case 733:{

                        mView.errorInvalidWalletName();
                        break;
                    }

                    case 401:{

                        mView.errorAddWalletAsAnAnonymous();
                        break;
                    }


                }
            }


        });
    }
}
