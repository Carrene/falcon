package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.model.Rate;
import de.netalic.falcon.repository.exchangeRate.ExchangeRateRepository;
import de.netalic.falcon.repository.wallet.WalletRepository;

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
        WalletRepository.getInstance().getAll(deal -> {

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
        WalletRepository.getInstance().charge(id, amount, deal -> {

            if (deal.getThrowable() != null) {

                mChargeAmountView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {


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
    public void finalize(double amount, String braintreeNonce, String chargeDataToken) {

        WalletRepository.getInstance().finalize(amount, braintreeNonce, chargeDataToken, deal -> {

            System.out.println(deal.getResponse().code());
        });
    }

    @Override
    public void exchangeRate(Rate rate) {

        mChargeAmountView.showProgressBar();

        ExchangeRateRepository.getInstance().get(rate.getCurrency().getCode(), deal -> {

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
