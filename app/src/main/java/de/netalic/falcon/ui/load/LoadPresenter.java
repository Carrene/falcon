package de.netalic.falcon.ui.load;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoadPresenter implements LoadContract.Presenter {

    @NonNull
    private final LoadContract.View mChargeView;

    public LoadPresenter(@NonNull LoadContract.View chargeView) {

        mChargeView = checkNotNull(chargeView);
        mChargeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {
        //TODO: (Ehsan) we dont have anymore status code
        mChargeView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable() == null) {
                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeView.setListWallet(deal.getModel());
                        break;

                    }
                }
            } else {

            }

        });
        mChargeView.dismissProgressBar();
    }

    @Override
    public void listExchangeRate() {

        mChargeView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {


                mChargeView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeView.setRateList(deal.getResponse().body());
                        break;
                    }

                }
            }

        });
        mChargeView.dismissProgressBar();
    }

    @Override
    public void exchangeRate(String codeCurrency) {

        mChargeView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).get(codeCurrency, deal -> {

            if (deal.getThrowable() != null) {

                mChargeView.dismissProgressBar();

            } else {


                switch (deal.getResponse().code()) {

                    case 200: {


                        mChargeView.updateExchangeRateCurrency(deal.getModel());
                        break;
                    }
                    case 709: {

                        mChargeView.showErrorInvalidCurrency();
                        break;
                    }
                    case 721: {

                        mChargeView.showErrorRatesDoesNotExists();
                        break;
                    }

                }
            }

        });
        mChargeView.dismissProgressBar();

    }


    @Override
    public void charge(int id, double amount, int verifyRateId) {

        mChargeView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).startCharge(id, amount, verifyRateId, deal -> {

            if (deal.getThrowable() != null) {

                mChargeView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeView.showChargePaymentConfirmation(deal.getModel());

                        break;
                    }

                    case 700: {

                        mChargeView.showErrorInvalidWalletId();
                        break;
                    }

                    case 702: {

                        mChargeView.showErrorInvalidAmount();
                        break;
                    }

                    case 703: {

                        mChargeView.showErrorAmountIsSmallerThanLowerBound();
                        break;
                    }

                    case 704: {

                        mChargeView.showErrorAmountIsGreaterThanUpperBound();
                        break;
                    }

                    case 725: {

                        mChargeView.showErrorChargeIsUnAvailable();
                        break;
                    }

                    case 400: {

                        mChargeView.showErrorVerifyRateIdMissing();
                        break;
                    }

                    case 401: {

                        mChargeView.showErrorStartATransferAsAnAnonymous();
                        break;
                    }
                    case 605: {

                        mChargeView.showErrorVerifyRateIsOutdatedOrItHasWrongCurrency();
                        break;
                    }

                    case 728: {

                        mChargeView.showErrorInvalidVerifyRateId();
                    }
                }

            }

        });
        mChargeView.dismissProgressBar();
    }

}
