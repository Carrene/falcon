package de.netalic.falcon.ui.purchase;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

public class PurchasePresenter implements PurchaseContract.Presenter {

    private PurchaseContract.View mPurchaseView;


    public PurchasePresenter(PurchaseContract.View purchaseView) {
        mPurchaseView = purchaseView;
        mPurchaseView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {

        mPurchaseView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable()!=null){

            } else {
                switch (deal.getResponse().code()){

                    case 200:{

                        mPurchaseView.setListWallet(deal.getResponse().body());
                        break;
                    }
                }

            }
            mPurchaseView.dismissProgressBar();
        });


    }
}
