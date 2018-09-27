package de.netalic.falcon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class SettingActivity extends BaseActivity {

    private TextView mTextViewPhoneNumber;
    private TextView mTextViewEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ViewCompat.setTransitionName(findViewById(R.id.appbarlayout_setting_appbarlayout),
                "EXTRA_IMAGE");
        supportPostponeEnterTransition();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_setting_toolbar));

        mTextViewPhoneNumber = findViewById(R.id.textview_setting_phonenumber);
        mTextViewEmail = findViewById(R.id.textview_setting_email);

        setPhoneAndEmail();
        setupBackButton();

        SettingFragment settingFragment = (SettingFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_setting_fragmentcontainer);
        if (settingFragment == null) {

            settingFragment = SettingFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), settingFragment, R.id.framelayout_setting_fragmentcontainer);
        }
        new SettingPresenter(settingFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_setting;
    }

    @Override
    protected String getActionbarTitle() {

        return "Title";
    }

    private void setPhoneAndEmail() {

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());
        String phone = (String) tokenBody.get("phone");
        String email = (String) tokenBody.get("email");


        if (email == null) {
            mTextViewEmail.setText("Email not set");
        } else {
            mTextViewEmail.setText(email);
        }

        if (phone == null) {

            mTextViewPhoneNumber.setText("phoneNumber not set");
        } else {

            mTextViewPhoneNumber.setText(phone);
        }
    }
}
