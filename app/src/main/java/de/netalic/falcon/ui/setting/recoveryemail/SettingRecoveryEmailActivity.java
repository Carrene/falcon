package de.netalic.falcon.ui.setting.recoveryemail;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class SettingRecoveryEmailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        SettingRecoveryEmailFragment settingRecoveryEmailFragment=(SettingRecoveryEmailFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_settingrecoveryemail_fragmentcontainer);
        if (settingRecoveryEmailFragment==null){

            settingRecoveryEmailFragment=SettingRecoveryEmailFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),settingRecoveryEmailFragment,R.id.framelayout_settingrecoveryemail_fragmentcontainer);
        }
            new SettingRecoveryEmailPresenter(settingRecoveryEmailFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_settingrecoveryemail;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.settingrecoveryemail_recoveryemail);
    }
}
