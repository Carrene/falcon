package de.netalic.falcon.ui.addresses;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class QrCodeAddressesFragment extends Fragment implements QrCodeAddressesContract.View {

    private QrCodeAddressesContract.Presenter mQrCodePresenter;
    private TextView mTextViewWalletType;
    private ImageView mImageViewQrCode;
    private Bitmap mBitmapQrCode;
    private View mRoot;
    private String mCurrencyCode;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Addresses";
    public static final String QR = "qr";
    public static final String CURRENCY_CODE = "currencyCode";
    private static final int IMAGE_QUALITY = 100;
    private View mScreenshotView;
    private Button mButtonNavigationToDashboard;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_qrcodeaddresses, null);
        mBitmapQrCode = getArguments().getParcelable(QR);
        mCurrencyCode = getArguments().getString(CURRENCY_CODE);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        setImageQr();
        setWalletAddress();
        initListener();
    }

    @Override
    public void setPresenter(QrCodeAddressesContract.Presenter presenter) {

        mQrCodePresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static QrCodeAddressesFragment newInstance(Bitmap bitmap, String walletAddress) {

        QrCodeAddressesFragment fragment = new QrCodeAddressesFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(QR, bitmap);
        bundle.putString(CURRENCY_CODE, walletAddress);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent() {

        mTextViewWalletType = mRoot.findViewById(R.id.textview_qrcodeaddresses_wallettype);
        mImageViewQrCode = mRoot.findViewById(R.id.imageview_qrcodeaddresses_qr);
        mScreenshotView = mRoot.findViewById(R.id.imageview_qrcodeaddresses_qr);
        mButtonNavigationToDashboard = mRoot.findViewById(R.id.button_qrcodeaddresses_navigationtodashboard);
    }

    public void setImageQr() {

        mImageViewQrCode.setImageBitmap(mBitmapQrCode);
    }

    private void setWalletAddress() {

        mTextViewWalletType.setText(mCurrencyCode + " " + getString(R.string.qrcodeaddresses_wallet));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_everywhere_sharedownloadtoolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_everywhere_download: {

                requestPermissionSave();
                break;
            }

            case R.id.item_everywhere_share: {

                requestPermissionShare();
                break;
            }
        }
        return true;
    }

    private void requestPermissionShare() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = ScreenshotUtil.saveScreenshot(getString(R.string.everywhere_image),ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH, CHARGE_PATH);
            ScreenshotUtil.shareScreenshot(file, getContext());
        }
    }

    private void requestPermissionSave() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
        else {

            ScreenshotUtil.saveScreenshot(getString(R.string.everywhere_image),ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH, CHARGE_PATH);
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.qrcodecompleted_imagesaved), getContext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissionallowed), getContext());
        } else {

            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissiondenied), getContext());
        }
    }

    private void initListener() {

        mButtonNavigationToDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DashboardActivity.class);
            startActivity(intent);

        });
    }

}
