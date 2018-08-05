package de.netalic.falcon.presenter;

import static  com.google.common.base.Preconditions.checkNotNull;

public class WithdrawAmountPresenter implements WithdrawAmountContract.Presenter {

    private WithdrawAmountContract.View mViewWithdrawAmount;


    public WithdrawAmountPresenter(WithdrawAmountContract.View viewWithdrawAmount) {
        mViewWithdrawAmount =checkNotNull(viewWithdrawAmount);
        mViewWithdrawAmount.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
