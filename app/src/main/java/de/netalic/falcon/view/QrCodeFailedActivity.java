package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeFailedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class QrCodeFailedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        QrCodeFailedFragment qrCodeFailedFragment=(QrCodeFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_qrcodefailed_fragmentcontainer);
        if (qrCodeFailedFragment==null){

            qrCodeFailedFragment=QrCodeFailedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),qrCodeFailedFragment,R.id.framelayout_qrcodefailed_fragmentcontainer);

        }

        new QrCodeFailedPresenter(qrCodeFailedFragment);
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
