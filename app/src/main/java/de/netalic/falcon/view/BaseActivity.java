package de.netalic.falcon.view;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.netalic.falcon.R;
import de.netalic.falcon.network.CheckInternetConnectivity;

public abstract class BaseActivity extends AppCompatActivity implements CheckInternetConnectivity.NetworkStateChangeListener {
    private Toolbar mToolbar;
    private CheckInternetConnectivity checkInternetConnectivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupToolbar();
        initInternetConnection();
    }

    private void initInternetConnection() {

        checkInternetConnectivity = new CheckInternetConnectivity(this);
        this.registerReceiver(checkInternetConnectivity, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

    }

    public void setupToolbar() {

        mToolbar = findViewById(R.id.toolbar_registration);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Nullable
    public Toolbar getToolbar() {

        return mToolbar;
    }

    @Override
    protected void onDestroy() {

        this.checkInternetConnectivity.removeListener();
        this.unregisterReceiver(checkInternetConnectivity);
        super.onDestroy();
    }

    protected abstract int getLayoutId();

    protected abstract String getActionbarTitle();

    @Override
    public void networkAvailable() {

        getSupportActionBar().setTitle(getActionbarTitle());
    }

    @Override
    public void networkUnavailable() {

        getSupportActionBar().setTitle("no internet");
    }
}
