package de.netalic.falcon.ui.send;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;

public class SendPresenter implements SendContract.Presenter {

    private SendContract.View mSendView;

    @Override
    public void start() {

    }

    public SendPresenter(SendContract.View sendView) {
        mSendView = sendView;
        mSendView.setPresenter(this);

    }

    @Override
    public void startTransfer(int sourceWalletId, String destinationWalletId, float amount) {

        mSendView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).startTransfer(sourceWalletId, destinationWalletId, amount, deal -> {

            if (deal.getThrowable() != null) {

                mSendView.dismissProgressBar();


            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mSendView.navigationToSendConfirmation(deal.getResponse().body());
                        break;
                    }

                    case 700: {

                        mSendView.showError700();
                        break;

                    }

                    case 727: {

                        mSendView.showError727();
                        break;
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Source wallet not found")) {
                            mSendView.showErrorSourceWalletNotFound404();
                        } else {
                            mSendView.showErrorTryingToTransferFromOtherWallet404();
                        }
                        break;
                    }


                    case 601: {

                        mSendView.showError601();
                        break;
                    }

                    case 702: {

                        if (deal.getResponse().message().equals("Amount is negative")) {

                            mSendView.showErrorAmountIsNegative702();
                        }
                        if (deal.getResponse().message().equals("Amount is zero")) {
                            mSendView.showErrorAmountIsZero702();
                        } else {
                            mSendView.showErrorInvalidAmount702();
                        }
                        break;
                    }

                    case 600: {

                        mSendView.showError600();
                        break;
                    }

                    case 401: {

                        mSendView.showError401();
                        break;
                    }

                    case 602: {

                        mSendView.showError602();
                        break;
                    }
                }

            }
            mSendView.dismissProgressBar();
        });


    }

    @Override
    public void listExchangeRate() {

        mSendView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {

                mSendView.dismissProgressBar();
                mSendView.internetConnectionError();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mSendView.setRateList(deal.getResponse().body());
                        break;
                    }

                }
            }

            mSendView.dismissProgressBar();
        });

    }
}
