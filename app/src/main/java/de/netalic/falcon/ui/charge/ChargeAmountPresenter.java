package de.netalic.falcon.ui.charge;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.exchangeRate.ExchangeRateRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeAmountPresenter implements ChargeAmountContract.Presenter {

    @NonNull
    private final ChargeAmountContract.View mChargeAmountView;


    public ChargeAmountPresenter(ChargeAmountContract.View chargeView) {


        mChargeAmountView = checkNotNull(chargeView);
        mChargeAmountView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {

        mChargeAmountView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {
                mChargeAmountView.dismissProgressBar();

            } else {
                switch (deal.getResponse().code()) {

                    case 200: {


                        break;
                    }
                }

            }
            mChargeAmountView.dismissProgressBar();
        });

    }

    @Override
    public void charge(int id, double amount) {

        mChargeAmountView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).charge(id, amount, deal -> {

            if (deal.getThrowable() != null) {

                mChargeAmountView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeAmountView.showChargePaymentConfirmation(deal.getModel());

                        break;
                    }

                    case 700: {

                        mChargeAmountView.showErrorInvalidWalletId();
                        break;
                    }

                    case 702: {

                        mChargeAmountView.showErrorInvalidAmount();
                        break;
                    }

                    case 703: {

                        mChargeAmountView.showErrorAmountIsSmallerThanLowerBound();
                        break;
                    }

                    case 704: {

                        mChargeAmountView.showErrorAmountIsGreaterThanUpperBound();
                        break;
                    }
                }
                mChargeAmountView.dismissProgressBar();
            }

        });
    }


    @Override
    public void exchangeRate(Rate rate) {

        mChargeAmountView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(ExchangeRateRepository.class).get(rate.getCurrencyCode(), deal -> {

            if (deal.getThrowable() != null) {

                mChargeAmountView.dismissProgressBar();

            } else {


                switch (deal.getResponse().code()) {

                    case 200: {


                        mChargeAmountView.updateExchangeRateCurrency(deal.getModel());
                        break;
                    }
                    case 709: {

                        mChargeAmountView.showErrorInvalidCurrency();
                        break;
                    }
                    case 721: {

                        mChargeAmountView.showErrorRatesDoesNotExists();
                        break;
                    }

                }
                mChargeAmountView.dismissProgressBar();

            }

        });

    }
}
