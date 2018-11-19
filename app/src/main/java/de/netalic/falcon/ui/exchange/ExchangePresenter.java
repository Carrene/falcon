package de.netalic.falcon.ui.exchange;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

public class ExchangePresenter implements ExchangeContract.Presenter {

    private ExchangeContract.View mExchangeView;

    public ExchangePresenter(ExchangeFragment exchangeFragment) {
        mExchangeView = exchangeFragment;
        mExchangeView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void listExchangeRate() {

        mExchangeView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {


                mExchangeView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mExchangeView.setRateList(deal.getResponse().body());
                        mExchangeView.setWalletRate();
                        break;
                    }

                }
            }
            mExchangeView.dismissProgressBar();

        });
    }

    @Override
    public void transfer(int walletId, String address, float amount) {
        mExchangeView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).startTransfer(walletId, address, amount, deal -> {

            if (deal.getThrowable() != null) {

                mExchangeView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mExchangeView.navigationToExchangeConfirmation(deal.getResponse().body());
                        break;
                    }

                    case 700: {
                        
                        break;

                    }

                    case 727: {

                        break;
                    }

                    case 404: {

                        if (deal.getResponse().message().equals("Source wallet not found")) {

                        } else {

                        }
                        break;
                    }


                    case 601: {

                        break;
                    }

                    case 702: {

                        if (deal.getResponse().message().equals("Amount is negative")) {

                        }
                        if (deal.getResponse().message().equals("Amount is zero")) {

                        } else {
                        }
                        break;
                    }

                    case 600: {

                        break;
                    }

                    case 401: {

                        break;
                    }

                    case 602: {

                        break;
                    }
                }

            }
            mExchangeView.dismissProgressBar();
        });


    }

    @Override
    public void getWalletList() {

        mExchangeView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable() == null) {
                switch (deal.getResponse().code()) {

                    case 200: {

                        mExchangeView.setListWallet(deal.getModel());
                        break;

                    }
                }
            } else {

            }

        });
        mExchangeView.dismissProgressBar();
    }

}
