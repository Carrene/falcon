package de.netalic.falcon.presenter;

import static com.google.common.base.Preconditions.checkNotNull;
public class WithdrawPresenter implements WithdrawPresenterContract.Presenter {

    private final WithdrawPresenterContract.View mWithdrawView;



    public WithdrawPresenter(WithdrawPresenterContract.View withdrawView) {

        mWithdrawView = checkNotNull(withdrawView);
        mWithdrawView.setPresenter(this);

    }

    @Override
    public void start() {

    }
}
