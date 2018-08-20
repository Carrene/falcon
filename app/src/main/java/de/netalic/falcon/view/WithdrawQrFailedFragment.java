package de.netalic.falcon.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeFailedContract;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawQrFailedFragment extends Fragment implements QrCodeFailedContract.View {


    private QrCodeFailedContract.Presenter mQrCodeFailedPresenter;
    private View mRoot;
    private Button mButtonTryWithdraw;
    private Button mButtonDashboard;
    private View mScreenshotView;
    private static final int REQUEST_PERMISSIONS = 120;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Withdraw";
    private static final int IMAGE_QUALITY = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_withdrawqrfailed, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initListener();
        setHasOptionsMenu(true);
    }

    @Override
    public void setPresenter(QrCodeFailedContract.Presenter presenter) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static WithdrawQrFailedFragment newInstance() {

        return new WithdrawQrFailedFragment();
    }

    private void initUiComponent() {

        mScreenshotView = mRoot.findViewById(R.id.linearlayout_withdrawqrfailed_forscreenshot);
        mButtonTryWithdraw = mRoot.findViewById(R.id.button_qrcodefailed_trywithdraw);
        mButtonDashboard = mRoot.findViewById(R.id.button_qrcodefailed_dashborad);

    }

    private void initListener() {

        mButtonTryWithdraw.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), WithdrawActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        mButtonDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_withdrawqrcompletedqrfailed_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_withdrawqrcompletedqrfailed_download: {

                requestPermissionSave();
                break;
            }

            case R.id.item_withdrawqrcompletedqrfailed_share: {

                requestPermissionShare();
                break;
            }

        }
        return true;
    }


    private void requestPermissionShare() {


        int regEX = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (regEX != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(checkNotNull(getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH, CHARGE_PATH);
            ScreenshotUtil.shareScreenshot(file, getContext());


        }
    }

    private void requestPermissionSave() {


        int regEX = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (regEX != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(checkNotNull(getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView), IMAGE_QUALITY, ALPHA_PATH, CHARGE_PATH);
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.qrcodecompleted_imagesaved), getContext());


        }
    }
}
