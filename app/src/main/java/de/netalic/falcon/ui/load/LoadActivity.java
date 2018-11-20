package de.netalic.falcon.ui.load;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class LoadActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this, getToolbar(),1);

        Bundle bundle=getIntent().getExtras();
        Wallet selectedWallet=bundle.getParcelable(DashboardFragment.SELECTED_WALLET);

        LoadFragment loadFragment = (LoadFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_charge_fragmentcontainer);
        if (loadFragment == null) {
            loadFragment = LoadFragment.newInstance(selectedWallet);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), loadFragment, R.id.framelayout_charge_fragmentcontainer);
        }

        new LoadPresenter(loadFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_load;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.navigation_charge);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}
