package de.netalic.falcon.ui.purchase;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;

public class PurchaseScanQrCodePresenter implements PurchaseScanQrCodeContract.Presenter {

    private PurchaseScanQrCodeContract.View  mPurchaseScanQrCodeView;


    public PurchaseScanQrCodePresenter(PurchaseScanQrCodeContract.View purchaseScanQrCodeView) {

        mPurchaseScanQrCodeView = purchaseScanQrCodeView;
        mPurchaseScanQrCodeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void startTransfer(int sourceWalletId, String destinationWalletId, float amount) {

        mPurchaseScanQrCodeView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).startTransfer(sourceWalletId, destinationWalletId, amount, deal -> {

            if (deal.getThrowable() != null) {

                mPurchaseScanQrCodeView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mPurchaseScanQrCodeView.navigationToTransferConfirmation(deal.getResponse().body());
                        break;
                    }

                    case 700: {

                        mPurchaseScanQrCodeView.showError700();
                        break;

                    }

                    case 727: {

                        mPurchaseScanQrCodeView.showError727();
                        break;
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Source wallet not found")) {
                            mPurchaseScanQrCodeView.showErrorSourceWalletNotFound404();
                        } else {
                            mPurchaseScanQrCodeView.showErrorTryingToTransferFromOtherWallet404();
                        }
                        break;
                    }


                    case 601: {

                        mPurchaseScanQrCodeView.showError601();
                        break;
                    }

                    case 702: {

                        if (deal.getResponse().message().equals("Amount is negative")) {

                            mPurchaseScanQrCodeView.showErrorAmountIsNegative702();
                        }
                        if (deal.getResponse().message().equals("Amount is zero")) {
                            mPurchaseScanQrCodeView.showErrorAmountIsZero702();
                        } else {
                            mPurchaseScanQrCodeView.showErrorInvalidAmount702();
                        }
                        break;
                    }

                    case 600: {

                        mPurchaseScanQrCodeView.showError600();
                        break;
                    }

                    case 401: {

                        mPurchaseScanQrCodeView.showError401();
                        break;
                    }

                    case 602: {

                        mPurchaseScanQrCodeView.showError602();
                        break;
                    }
                }

            }
            mPurchaseScanQrCodeView.dismissProgressBar();
        });

    }
}
