package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;

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


        mTransferPayeeView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).startTransfer(sourceWalletId, destinationWalletId, amount, deal -> {

            if (deal.getThrowable() != null) {

                mTransferPayeeView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mTransferPayeeView.navigationToTransferConfirmation(deal.getResponse().body());
                        break;
                    }

                    case 700: {

                        mTransferPayeeView.showError700();
                        break;

                    }

                    case 727: {

                        mTransferPayeeView.showError727();
                        break;
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Source wallet not found")) {
                            mTransferPayeeView.showErrorSourceWalletNotFound404();
                        } else {
                            mTransferPayeeView.showErrorTryingToTransferFromOtherWallet404();
                        }
                        break;
                    }


                    case 601: {

                        mTransferPayeeView.showError601();
                        break;
                    }

                    case 702: {

                        if (deal.getResponse().message().equals("Amount is negative")) {

                            mTransferPayeeView.showErrorAmountIsNegative702();
                        }
                        if (deal.getResponse().message().equals("Amount is zero")) {
                            mTransferPayeeView.showErrorAmountIsZero702();
                        } else {
                            mTransferPayeeView.showErrorInvalidAmount702();
                        }
                        break;
                    }

                    case 600: {

                        mTransferPayeeView.showError600();
                        break;
                    }

                    case 401: {

                        mTransferPayeeView.showError401();
                        break;
                    }

                    case 602: {

                        mTransferPayeeView.showError602();
                        break;
                    }
                }

            }
            mTransferPayeeView.dismissProgressBar();
        });

    }
}
