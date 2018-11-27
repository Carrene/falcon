package de.netalic.falcon.ui.setting.basic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRealmRepository;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class SettingActivity extends BaseActivity {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        setupToolbar();
        setupBackButton();
        initUiComponent();
        setName();

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

        return "";
    }

    private void initUiComponent() {

        mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));

    }

    private void setName() {
        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance());
        String token = sharedPreferencesJwtPersistor.get();
        int id = (int) Parser.getTokenBody(token).get("id");
        RepositoryLocator.getInstance().getRepository(UserRepository.class).get(id, deal -> {
            if (deal.getThrowable() == null) {
                mCollapsingToolbarLayout.setTitle(deal.getModel().getPhone());
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

}
