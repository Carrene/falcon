package de.netalic.falcon.ui.purchase;

import android.graphics.Bitmap;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.withdraw.WithDrawQrCompletedFragment;
import de.netalic.falcon.ui.withdraw.WithdrawQrCodeCompletedPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class PurchaseGenerateQrCodeActivity extends BaseActivity {

    public static final String QR_CODE="qr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bitmap bitmap = getIntent().getParcelableExtra(QR_CODE);

        WithDrawQrCompletedFragment withDrawQrCompletedFragment = (WithDrawQrCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_qrcodecompleted_fragmentcontainer);

        if (withDrawQrCompletedFragment == null) {

            withDrawQrCompletedFragment = WithDrawQrCompletedFragment.newInstance(bitmap);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), withDrawQrCompletedFragment, R.id.framelayout_qrcodecompleted_fragmentcontainer);
        }
        new WithdrawQrCodeCompletedPresenter(withDrawQrCompletedFragment);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchasegenerateqrcode;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.qrcodecompleted_success);
    }
}
