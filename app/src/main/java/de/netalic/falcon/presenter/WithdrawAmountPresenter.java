package de.netalic.falcon.presenter;

import de.netalic.falcon.model.Rate;
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
