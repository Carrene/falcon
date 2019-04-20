package de.netalic.falcon.ui.addresses;

import android.graphics.Bitmap;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class QrCodeAddressesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        Bitmap bitmap = getIntent().getParcelableExtra(QrCodeAddressesFragment.QR);
        Bundle bundle=getIntent().getExtras();
        String walletAddress=bundle.getString(QrCodeAddressesFragment.CURRENCY_CODE);

        QrCodeAddressesFragment qrCodeAddressesFragment=(QrCodeAddressesFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_qrcodeaddresses_fragmentcontainer);
        if (qrCodeAddressesFragment==null){

            qrCodeAddressesFragment=QrCodeAddressesFragment.newInstance(bitmap,walletAddress);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),qrCodeAddressesFragment,R.id.framelayout_qrcodeaddresses_fragmentcontainer);
        }
        new QrCodeAddressesPresenter(qrCodeAddressesFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcodeaddresses;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.qrcodeaddresses_toolbar);
    }
}
