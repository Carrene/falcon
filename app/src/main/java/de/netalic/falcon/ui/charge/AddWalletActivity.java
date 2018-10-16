package de.netalic.falcon.ui.charge;

import android.content.Intent;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class AddWalletActivity extends BaseActivity {

    private String mCurrency;
    private AddWalletFragment mAddWalletFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();


        mAddWalletFragment = (AddWalletFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_addwallet_fragmentcontainer);

        if (mAddWalletFragment == null) {

            mAddWalletFragment = AddWalletFragment.newInstance(mCurrency);

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), mAddWalletFragment, R.id.framelayout_addwallet_fragmentcontainer);
        }

        new AddWalletPresenter(mAddWalletFragment);

    }


    public String getCurrency() {


        return mCurrency;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        mCurrency = data.getStringExtra(AddWalletFragment.SELECTED_CURRENCY);

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
