package de.netalic.falcon.ui.withdraw;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class WithdrawQrFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WithdrawQrFailedFragment withdrawQrFailedFragment =(WithdrawQrFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_qrcodefailed_fragmentcontainer);
        if (withdrawQrFailedFragment ==null){

            withdrawQrFailedFragment = WithdrawQrFailedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), withdrawQrFailedFragment,R.id.framelayout_qrcodefailed_fragmentcontainer);

        }

        new WithdrawQrCodeFailedPresenter(withdrawQrFailedFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcodefailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.qrcodefailed_toolbar);
    }
}
