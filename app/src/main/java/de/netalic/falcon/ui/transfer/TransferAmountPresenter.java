package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;

import static com.google.common.base.Preconditions.checkNotNull;
public class TransferAmountPresenter implements TransferAmountContract.Presenter {

    private TransferAmountContract.View mTransferAmountView;


    public TransferAmountPresenter(TransferAmountContract.View transferAmountView) {
        mTransferAmountView =checkNotNull(transferAmountView);
        mTransferAmountView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void exchangeRate(Rate rate) {
        mTransferAmountView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).get(rate.getCurrencyCode(), deal -> {

            if (deal.getThrowable() != null) {

                mTransferAmountView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mTransferAmountView.updateExchangeRateCurrency(deal.getModel());
                        break;
                    }
                    case 709: {

                        mTransferAmountView.showErrorInvalidCurrency();
                        break;
                    }
                    case 721: {

                        mTransferAmountView.showErrorRatesDoesNotExists();
                        break;
                    }

                }
                mTransferAmountView.dismissProgressBar();

            }

        });
    }
}
