package de.netalic.falcon.presenter;

import java.util.List;

import de.netalic.falcon.model.Rate;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;
import de.netalic.falcon.repository.exchangeRate.ExchangeRateRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawAmountPresenter implements WithdrawAmountContract.Presenter {

    private WithdrawAmountContract.View mViewWithdrawAmount;


    public WithdrawAmountPresenter(WithdrawAmountContract.View viewWithdrawAmount) {
        mViewWithdrawAmount =checkNotNull(viewWithdrawAmount);
        mViewWithdrawAmount.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void exchangeRate(Rate rate) {
        mViewWithdrawAmount.showProgressBar();

        ExchangeRateRepository.getInstance().get(rate.getCurrencyCode(), deal -> {

            if (deal.getThrowable() != null) {

                mViewWithdrawAmount.dismissProgressBar();

            } else {


                switch (deal.getResponse().code()) {

                    case 200: {


                        mViewWithdrawAmount.updateExchangeRateCurrency(deal.getModel());
                        break;
                    }
                    case 709: {

                        mViewWithdrawAmount.showErrorInvalidCurrency();
                        break;
                    }
                    case 721: {

                        mViewWithdrawAmount.showErrorRatesDoesNotExists();
                        break;
                    }

                }
                mViewWithdrawAmount.dismissProgressBar();

            }

        });
    }

    @Override
    public void listExchangeRate() {

        mViewWithdrawAmount.showProgressBar();

        ExchangeRateRepository.getInstance().getAll(deal -> {

            if (deal.getThrowable()!=null){


                mViewWithdrawAmount.dismissProgressBar();
            }

            else {

                switch (deal.getResponse().code()){

                    case 200:{

                        mViewWithdrawAmount.setRateList(deal.getResponse().body());
                    }

                }
            }

        });
    }
}
