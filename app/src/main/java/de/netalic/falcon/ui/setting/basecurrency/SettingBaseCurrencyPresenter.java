package de.netalic.falcon.ui.setting.basecurrency;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.currency.CurrencyRepository;
import de.netalic.falcon.data.repository.user.UserRepository;

public class SettingBaseCurrencyPresenter implements SettingBaseCurrencyContract.Presenter {

    private SettingBaseCurrencyContract.View mSettingBaseCurrencyView;


    public SettingBaseCurrencyPresenter(SettingBaseCurrencyContract.View baseCurrencyView) {
        mSettingBaseCurrencyView = baseCurrencyView;
        mSettingBaseCurrencyView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getCurrencyList() {

        mSettingBaseCurrencyView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(CurrencyRepository.class).getAll(deal -> {

            if (deal.getThrowable() == null) {

                switch (deal.getResponse().code()) {

                    case 200: {
                        mSettingBaseCurrencyView.setCurrencyList(deal.getResponse().body());
                        break;
                    }

                }

            }


        });
        mSettingBaseCurrencyView.dismissProgressBar();
    }

    @Override
    public void changeBaseCurrency(int userId, String baseCurrency) {


        mSettingBaseCurrencyView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(UserRepository.class).updateCurrency(userId, baseCurrency, deal -> {

            if (deal.getThrowable() == null) {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mSettingBaseCurrencyView.setBaseCurrency(deal.getModel());
                        break;
                    }

                    case 400: {

                        mSettingBaseCurrencyView.baseCurrencyCodeMissingInForm();
                        break;

                    }

                    case 401: {

                        mSettingBaseCurrencyView.tryingToPassWithUnauthorizedMember();
                        break;
                    }

                    case 404: {

                        mSettingBaseCurrencyView.clientNotFoundOrClientIdIsInvalid();
                        break;
                    }

                    case 732: {

                        mSettingBaseCurrencyView.invalidCurrencyCode();
                        break;
                    }
                }

            } else {


            }


            mSettingBaseCurrencyView.dismissProgressBar();
        });

    }
}
