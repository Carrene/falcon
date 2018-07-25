package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargePresenter implements ChargeContract.Presenter {

    @NonNull
    private final ChargeContract.View mChargeView;

    public ChargePresenter(@NonNull ChargeContract.View chargeView) {

        mChargeView = checkNotNull(chargeView);
        mChargeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {
        //TODO: (Milad) handle other status codes
        mChargeView.showProgressBar();
        WalletRepository.getInstance().getAll(deal -> {

            if (deal.getThrowable() == null) {
                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeView.setListWallet(deal.getModel());
                        mChargeView.dismissProgressBar();
                    }
                }
            } else {
                //TODO: handle status codes
            }
        });
    }
}
