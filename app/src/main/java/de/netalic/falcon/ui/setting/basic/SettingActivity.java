package de.netalic.falcon.ui.setting.basic;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;

public class SettingActivity extends BaseActivity {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        setupToolbar();
        setupBackButton();
        initUiComponent();

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

        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

    }

    public void setName(String phone) {
        mCollapsingToolbarLayout.setTitle("+" + phone);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

}
