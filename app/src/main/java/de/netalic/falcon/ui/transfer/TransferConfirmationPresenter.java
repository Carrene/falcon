package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.repository.receipt.ReceiptRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferConfirmationPresenter implements TransferConfirmationContract.Presenter {

    private TransferConfirmationContract.View mTransferConfirmationView;

    public TransferConfirmationPresenter(TransferConfirmationContract.View transferConfirmationView) {
        mTransferConfirmationView = checkNotNull(transferConfirmationView);
        mTransferConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void transfer(int sourceAddress, double amount, int walletAddress) {

        ReceiptRepository.getInstance().transfer(sourceAddress, walletAddress, amount, deal -> {

            mTransferConfirmationView.showProgressBar();
            if (deal.getThrowable() != null) {

                mTransferConfirmationView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mTransferConfirmationView.navigationToCompletedTransfer();

                    }

                    case 731: {

                        if (deal.getResponse().message().equals("Invalid source wallet address")) {


                            mTransferConfirmationView.showResponseCodeInvalidSourceWalletAddress();
                        } else {

                            mTransferConfirmationView.showResponseCodeInvalidDestinationWalletAddress();

                        }
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Source wallet not found")) {


                            mTransferConfirmationView.showResponseCodeSourceWalletNotFound();
                        } else {

                            mTransferConfirmationView.showResponseTryingToTransferFromAnotherClientWallet();
                        }

                    }
                    case 707: {

                        mTransferConfirmationView.showResponseInsufficientBalance();
                    }

                    case 702: {

                        if (deal.getResponse().message().equals("Invalid amount")) {

                            mTransferConfirmationView.showResponseCodeInvalidAmount();
                        } else {

                            mTransferConfirmationView.showResponseCodeAmountIsNegative();
                        }
                    }

                    case 730: {

                        mTransferConfirmationView.showResponseCodeSourceAndDestinationIsSame();
                    }
                }

            }
            mTransferConfirmationView.dismissProgressBar();


        });
    }
}
