package de.netalic.falcon.ui.transfer;

import android.content.Intent;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class TransferActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this,getToolbar(),4);
        TransferFragment transferFragment = (TransferFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transfer_fragmentcontainer);

        if (transferFragment == null) {

            transferFragment = TransferFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), transferFragment, R.id.framelayout_transfer_fragmentcontainer);

        }

        new TransferPresenter(transferFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.transfer_toolbar);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent=new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}
