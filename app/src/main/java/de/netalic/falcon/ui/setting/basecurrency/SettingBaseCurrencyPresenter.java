package de.netalic.falcon.ui.setting.basecurrency;

public class SettingBaseCurrencyPresenter implements SettingBaseCurrencyContract.Presenter {

    private SettingBaseCurrencyContract.View mBaseCurrencyView;


    public SettingBaseCurrencyPresenter(SettingBaseCurrencyContract.View baseCurrencyView) {
        mBaseCurrencyView = baseCurrencyView;
        mBaseCurrencyView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
