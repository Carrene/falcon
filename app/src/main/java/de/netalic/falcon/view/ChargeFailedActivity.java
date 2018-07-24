package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;

public class ChargeFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chargefailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.chargefailed_failed);
    }
}
