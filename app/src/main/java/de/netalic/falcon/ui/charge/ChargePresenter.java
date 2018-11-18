package de.netalic.falcon.ui.charge;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

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
            mChargeView.dismissProgressBar();

        });
    }

}
