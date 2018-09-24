package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;
import de.netalic.falcon.ui.transfer.TransferPayeeContract.Presenter;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferPayeePresenter implements TransferPayeeContract.Presenter {

    private TransferPayeeContract.View mTransferPayeeView;

    public TransferPayeePresenter(TransferPayeeContract.View transferPayeeView) {
        mTransferPayeeView = checkNotNull(transferPayeeView);
        mTransferPayeeView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void startTransfer(int sourceWalletId, String destinationWalletId, float amount) {

        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).startTransfer(sourceWalletId, destinationWalletId, amount, deal -> {

            if (deal.getThrowable()!=null){


            }

            else {

                switch (deal.getResponse().code()){

                    case 200:{

                        mTransferPayeeView.navigationToTransferConfirmation();
                    }
                }
            }
        });

    }
}
