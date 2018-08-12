package de.netalic.falcon.view;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeCompletedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class QrCodeCompletedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        QrCodeCompletedFragment qrCodeCompletedFragment=(QrCodeCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_qrcodecompleted_fragmentcontainer);
        if (qrCodeCompletedFragment==null){

            qrCodeCompletedFragment=QrCodeCompletedFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),qrCodeCompletedFragment,R.id.framelayout_qrcodecompleted_fragmentcontainer);
        }
        new QrCodeCompletedPresenter(qrCodeCompletedFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcodecompleted;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.qrcodecompleted_toolbar);
    }
}
