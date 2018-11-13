package de.netalic.falcon.ui.addresses;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class AddressesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationDrawerUtil.getDrawer(this, getToolbar(), 2);

        AddressesFragment addressesFragment = (AddressesFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_addresses_fragmentcontainer);
        if (addressesFragment == null) {

            addressesFragment = AddressesFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), addressesFragment, R.id.framelayout_addresses_fragmentcontainer);
        }

        new AddressesPresenter(addressesFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addresses;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.addresses_toolbar);
    }
}
