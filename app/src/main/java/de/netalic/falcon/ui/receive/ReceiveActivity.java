package de.netalic.falcon.ui.receive;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class ReceiveActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this,getToolbar(),1);

        Bundle bundle=getIntent().getExtras();
        Wallet selectedWallet=bundle.getParcelable(DashboardFragment.SELECTED_WALLET);
        ReceiveFragment receiveFragment=(ReceiveFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_receive_fragmentcontainer);
        if (receiveFragment==null){

            receiveFragment=ReceiveFragment.newInstance(selectedWallet);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),receiveFragment,R.id.framelayout_receive_fragmentcontainer);

        }

        new ReceivePresenter(receiveFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_receive;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.receive_title);
    }
}
