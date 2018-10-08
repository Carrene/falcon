package de.netalic.falcon.ui.purchase;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class PurchaseScanQrCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        Bundle bundle=getIntent().getExtras();
        int walletId=bundle.getInt(PurchaseFragment.WALLET_ADDRESS);

        PurchaseScanQrCodeFragment purchaseScanQrCodeFragment=(PurchaseScanQrCodeFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_purchasescanqrcode_fragmentcontainer);
        if (purchaseScanQrCodeFragment==null){

            purchaseScanQrCodeFragment=PurchaseScanQrCodeFragment.newInstance(walletId);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),purchaseScanQrCodeFragment,R.id.framelayout_purchasescanqrcode_fragmentcontainer);

        }
        new PurchaseScanQrCodePresenter(purchaseScanQrCodeFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_purchasescanqrcode;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.purchasescanqrcode_toolbar);
    }
}
