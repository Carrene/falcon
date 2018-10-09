package de.netalic.falcon.ui.setting.basecurrency;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.currency.CurrencyRepository;

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

      RepositoryLocator.getInstance().getRepository(CurrencyRepository.class).getAll(deal -> {

          if (deal.getThrowable()==null){

              switch (deal.getResponse().code()){

                  case 200:{
                      mSettingBaseCurrencyView.setCurrencyList(deal.getResponse().body());
                      break;
                  }
              }

          }

      });

    }
}
