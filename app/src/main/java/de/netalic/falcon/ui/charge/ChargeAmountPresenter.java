package de.netalic.falcon.ui.charge;

import android.support.annotation.NonNull;

import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeAmountPresenter implements ChargeAmountContract.Presenter {

    @NonNull
    private final ChargeAmountContract.View mChargeAmountView;
    SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
    Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());

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
    public void charge(int id, double amount, int verifyRateId) {

        mChargeAmountView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).startCharge(id, amount, verifyRateId, deal -> {

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

                    case 725: {

                        mChargeAmountView.showErrorChargeIsUnAvailable();
                        break;
                    }

                    case 400: {

                        mChargeAmountView.showErrorVerifyRateIdMissing();
                        break;
                    }

                    case 401: {

                        mChargeAmountView.showErrorStartATransferAsAnAnonymous();
                        break;
                    }
                    case 605: {

                        mChargeAmountView.showErrorVerifyRateIsOutdatedOrItHasWrongCurrency();
                        break;
                    }

                    case 728: {

                        mChargeAmountView.showErrorInvalidVerifyRateId();
                    }
                }

            }
            mChargeAmountView.dismissProgressBar();
        });
    }


    @Override
    public void exchangeRate(String codeCurrency) {

        mChargeAmountView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).get(codeCurrency, deal -> {

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
            }
            mChargeAmountView.dismissProgressBar();
        });

    }

    @Override
    public void getBaseCurrency() {

        mChargeAmountView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(UserRepository.class).get((int) tokenBody.get("id"), deal -> {

            if (deal.getThrowable() == null) {

                mChargeAmountView.setBaseCurrency(deal.getModel());

            } else {

                mChargeAmountView.setBaseCurrencyNotSet();
            }
        });
        mChargeAmountView.dismissProgressBar();
    }
}
