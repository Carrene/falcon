package de.netalic.falcon.ui.withdraw;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class WithdrawActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setupBackButton();

        WithdrawFragment withdrawFragment=(WithdrawFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_withdraw_fragmentcontainer);
        if (withdrawFragment==null){

            withdrawFragment=WithdrawFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),withdrawFragment,R.id.framelayout_withdraw_fragmentcontainer);
        }
        new WithdrawPresenter(withdrawFragment);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.withdraw_toolbar);
    }
}
