package de.netalic.falcon.common.listcurrency;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;

public class ListCurrencyPresenter implements ListCurrencyContract.Presenter {


    private ListCurrencyContract.View mListCurrencyView;

    public ListCurrencyPresenter(ListCurrencyContract.View listCurrencyView) {
        mListCurrencyView = listCurrencyView;
        mListCurrencyView.setPresenter(this);
    }

    @Override
    public void getCurrencyList() {

        mListCurrencyView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() == null) {

                switch (deal.getResponse().code()) {

                    case 200: {
                        mListCurrencyView.setCurrencyList(deal.getResponse().body());
                        break;
                    }

                }

            }

        });
        mListCurrencyView.dismissProgressBar();

    }

    @Override
    public void start() {

    }
}
