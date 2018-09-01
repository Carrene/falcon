package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.repository.wallet.WalletRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferPresenter implements TransferContract.Presenter {

    private TransferContract.View mTransferView;


    public TransferPresenter(TransferContract.View transferView) {
        mTransferView = checkNotNull(transferView);
        mTransferView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {

        mTransferView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {


            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mTransferView.setWalletList(deal.getResponse().body());
                        break;

                    }


                }
            }
            mTransferView.dismissProgressBar();
        });

    }
}
