package de.netalic.falcon.view;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;

import de.netalic.falcon.R;
import de.netalic.falcon.network.CheckInternetConnectivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity implements CheckInternetConnectivity.NetworkStateChangeListener {

    private Toolbar mToolbar;
    private CheckInternetConnectivity checkInternetConnectivity;
    private MaterialDialog materialDialog;

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

        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    public void setupBackButton() {

        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Nullable
    public Toolbar getToolbar() {

        return mToolbar;
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        this.checkInternetConnectivity.removeListener();
        this.unregisterReceiver(checkInternetConnectivity);
    }

    protected abstract int getLayoutId();

    protected abstract String getActionbarTitle();

    @Override
    public void networkAvailable() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getActionbarTitle());
        }
    }

    @Override
    public void networkUnavailable() {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Connection error...");
        }
    }

    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {
                Intent intent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void showMaterialDialog() {

        Context context = this;
        MaterialDialog.Builder mMaterialDialogBuilder = new MaterialDialog.Builder(context)
                .title(context.getString(R.string.materialdialogutil_pleasewait))
                .content(context.getString(R.string.materialdialogutil_isloading))
                .progress(true, R.dimen.max_materialutil_materialutil)
                .negativeText("Cancel")
                .cancelable(false);
        materialDialog = mMaterialDialogBuilder.build();
        materialDialog.show();

        materialDialog.getBuilder().onNegative((dialog, which) -> materialDialog.dismiss());
    }

    protected void dismissMaterialDialog() {

        materialDialog.dismiss();
    }
//    @Override
//    public void onBackPressed() {
//
//        if (mDrawer != null && mDrawer.isDrawerOpen()) {
//            mDrawer.closeDrawer();
//        } else {
//            super.onBackPressed();
//
//        }
//    }
}