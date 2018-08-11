package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.WithdrawAmountPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class WithdrawAmountActivity extends BaseActivity {

    private static final String ARGUMENT_WALLET="WALLET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getExtras() == null) {
            throw new RuntimeException("Wallet should not be null");
        }
        Wallet wallet=getIntent().getExtras().getParcelable(ARGUMENT_WALLET);

        WithdrawAmountFragment withdrawAmountFragment = (WithdrawAmountFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_withdrawamount_fragmentcontainer);
        if (withdrawAmountFragment == null) {

            withdrawAmountFragment = WithdrawAmountFragment.newInstance(wallet);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), withdrawAmountFragment, R.id.framelayout_withdrawamount_fragmentcontainer);

        }
        new WithdrawAmountPresenter(withdrawAmountFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdrawamount;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.withdrawamount_toolbar);
    }
}
