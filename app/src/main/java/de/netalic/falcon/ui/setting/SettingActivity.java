package de.netalic.falcon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ViewCompat.setTransitionName(findViewById(R.id.app_bar_layout),
                "EXTRA_IMAGE");
        supportPostponeEnterTransition();
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_setting;
    }

    @Override
    protected String getActionbarTitle() {

        return "Title";
    }
}
