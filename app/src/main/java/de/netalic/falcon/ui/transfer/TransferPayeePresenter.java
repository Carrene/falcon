package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.repository.receipt.ReceiptRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;
import de.netalic.falcon.ui.transfer.TransferPayeeContract.Presenter;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferPayeePresenter implements Presenter {

    private TransferPayeeContract.View mTransferPayeeView;

    public TransferPayeePresenter(TransferPayeeContract.View transferPayeeView) {
        mTransferPayeeView = checkNotNull(transferPayeeView);
        mTransferPayeeView.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void transfer(int sourceAddress, double amount, int walletAddress) {

        mTransferPayeeView.showProgressBar();
        ReceiptRepository.getInstance().transfer(sourceAddress, walletAddress, amount, deal -> {

            if (deal.getThrowable() != null) {


            } else {


                switch (deal.getResponse().code()) {

                    case 200: {

                        mTransferPayeeView.showResponseCode();


                    }

                }
            }


        });

    }
}
