package de.netalic.falcon.view;

import android.graphics.Bitmap;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeCompletedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class WithdrawQrCompletedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Bitmap bitmap = getIntent().getParcelableExtra("qr");

        WithDrawQrCompletedFragment withDrawQrCompletedFragment = (WithDrawQrCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_qrcodecompleted_fragmentcontainer);

        if (withDrawQrCompletedFragment == null) {

            withDrawQrCompletedFragment = WithDrawQrCompletedFragment.newInstance(bitmap);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), withDrawQrCompletedFragment, R.id.framelayout_qrcodecompleted_fragmentcontainer);
        }
        new QrCodeCompletedPresenter(withDrawQrCompletedFragment);
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
