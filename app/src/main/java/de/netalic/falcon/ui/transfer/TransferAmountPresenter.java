package de.netalic.falcon.ui.transfer;

import de.netalic.falcon.data.repository.exchangeRate.ExchangeRateRepository;
import de.netalic.falcon.model.Rate;

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

        ExchangeRateRepository.getInstance().get(rate.getCurrencyCode(), deal -> {

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
