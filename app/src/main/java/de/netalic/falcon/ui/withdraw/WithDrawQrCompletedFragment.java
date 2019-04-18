package de.netalic.falcon.ui.withdraw;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import java.io.File;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;


public class WithDrawQrCompletedFragment extends Fragment implements WithdrawQrCodeCompletedContract.View {


    private WithdrawQrCodeCompletedContract.Presenter mQrCodeCompletedPresenter;
    private View mRoot;
    private View mScreenshotView;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Withdraw";
    private static final int IMAGE_QUALITY = 100;
    private static final int REQUEST_PERMISSIONS = 1;
    private Button mButtonNavigationToDashboard;
    private Bitmap mBitmapQrCode;
    private ImageView mImageViewQr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_withdrawqrcompleted, null);
        setHasOptionsMenu(true);
        mBitmapQrCode = getArguments().getParcelable(WithdrawQrCompletedActivity.QR);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        setImageQr();
        initListener();
    }

    @Override
    public void setPresenter(WithdrawQrCodeCompletedContract.Presenter presenter) {

        mQrCodeCompletedPresenter = checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static WithDrawQrCompletedFragment newInstance(Bitmap bitmap) {

        WithDrawQrCompletedFragment withDrawQrCompletedFragment = new WithDrawQrCompletedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(WithdrawQrCompletedActivity.QR, bitmap);
        withDrawQrCompletedFragment.setArguments(bundle);
        return withDrawQrCompletedFragment;
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
        } else {

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

    private void initUiComponent() {

        mScreenshotView = mRoot.findViewById(R.id.linearlayout_qrcodecompleted_mainview);
        mButtonNavigationToDashboard = mRoot.findViewById(R.id.button_qrcodecompleted_dashborad);
        mImageViewQr = mRoot.findViewById(R.id.imageView_withdrawqrcompleted_qr);
    }

    public void setImageQr() {

        mImageViewQr.setImageBitmap(mBitmapQrCode);
    }

    private void initListener() {

        mButtonNavigationToDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
