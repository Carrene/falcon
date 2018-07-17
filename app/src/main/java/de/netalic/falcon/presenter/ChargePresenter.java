package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;

import de.netalic.falcon.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargePresenter implements ChargeContract.Presenter {

    @NonNull
    private final ChargeContract.View mChargeView;


    public ChargePresenter(ChargeContract.View chargeView) {


        mChargeView = checkNotNull(chargeView);
        mChargeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {

        mChargeView.showProgressBar();
        WalletRepository.getInstance().getAll(deal -> {

            if (deal.getThrowable() != null) {
                mChargeView.dismissProgressBar();

            } else {
                switch (deal.getResponse().code()) {

                    case 200: {

                        mChargeView.setListWallet(deal.getResponse().body());
                        break;
                    }
                }

            }
            mChargeView.dismissProgressBar();
        });

    }

    @Override
    public void getToken(int id, double amount) {

        mChargeView.showProgressBar();
        WalletRepository.getInstance().getToken(id,amount,deal -> {

            if (deal.getThrowable()!=null){

                mChargeView.dismissProgressBar();
            }else {

                int a=deal.getResponse().code();
                switch (deal.getResponse().code()){

                    case 200:{



                    }
                }
            }

        });

    }


}
