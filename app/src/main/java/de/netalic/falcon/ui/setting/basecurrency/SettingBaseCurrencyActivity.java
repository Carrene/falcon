package de.netalic.falcon.ui.setting.basecurrency;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.setting.recoveryemail.SettingRecoveryEmailFragment;
import de.netalic.falcon.ui.setting.recoveryemail.SettingRecoveryEmailPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class SettingBaseCurrencyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        SettingBaseCurrencyFragment settingBaseCurrencyFragment = (SettingBaseCurrencyFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_basecurrency_fragmentcontainer);
        if (settingBaseCurrencyFragment == null) {

            settingBaseCurrencyFragment = SettingBaseCurrencyFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), settingBaseCurrencyFragment, R.id.framelayout_basecurrency_fragmentcontainer);
        }
        new SettingBaseCurrencyPresenter(settingBaseCurrencyFragment);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_basecurrency;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.basecurrency_changebaecurrency);
    }
}
