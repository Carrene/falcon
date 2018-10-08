package de.netalic.falcon.ui.purchase;

import com.google.gson.Gson;

import de.netalic.falcon.data.model.Purchase;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.user.UserRepository;

public class PurchaseAmountPresenter implements PurchaseAmountContract.Presenter {

    private PurchaseAmountContract.View mPurchaseAmountView;


    public PurchaseAmountPresenter(PurchaseAmountContract.View purchaseAmountView) {
        mPurchaseAmountView = purchaseAmountView;
        mPurchaseAmountView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void generateQrCode(Purchase purchase) {


        Gson gson=new Gson();
        String urlEncoded = gson.toJson(purchase);

        RepositoryLocator.getInstance().getRepository(UserRepository.class).get(null, deal -> {

            if (deal.getThrowable() == null) {


                mPurchaseAmountView.setQrCode(urlEncoded);


            }
        });
    }

    @Override
    public void exchangeRate(Rate rate) {

        mPurchaseAmountView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).get(rate.getCurrencyCode(), deal -> {

            if (deal.getThrowable() != null) {

                mPurchaseAmountView.dismissProgressBar();

            } else {


                switch (deal.getResponse().code()) {

                    case 200: {


                        mPurchaseAmountView.updateExchangeRateCurrency(deal.getModel());
                        break;
                    }
                    case 709: {

                        mPurchaseAmountView.showErrorInvalidCurrency();
                        break;
                    }
                    case 721: {

                        mPurchaseAmountView.showErrorRatesDoesNotExists();
                        break;
                    }

                }
            }
            mPurchaseAmountView.dismissProgressBar();
        });
    }
}
