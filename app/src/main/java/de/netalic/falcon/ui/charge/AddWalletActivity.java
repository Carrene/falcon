package de.netalic.falcon.ui.charge;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class AddWalletActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AddWalletFragment addWalletFragment=(AddWalletFragment)getSupportFragmentManager().findFragmentById(R.id.framelayout_addwallet_fragmentcontainer);

        if (addWalletFragment==null){

            addWalletFragment=AddWalletFragment.newInstance();

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),addWalletFragment,R.id.framelayout_addwallet_fragmentcontainer);
        }

        new AddWalletPresenter(addWalletFragment);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addwallet;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.addwallet_toolbar);
    }
}
