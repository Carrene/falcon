package de.netalic.falcon.view;

import android.Manifest;
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

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeCompletedContract;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;


public class QrCodeCompletedFragment extends Fragment implements QrCodeCompletedContract.View {


    private QrCodeCompletedContract.Presenter mQrCodeCompletedPresenter;
    private View mRoot;
    private View mScreenshotView;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String CHARGE_PATH = "/Charge";
    private static final int IMAGE_QUALITY = 100;
    private static final int REQUEST_PERMISSIONS = 120;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_qrcodecompleted,null);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestPermission();
        initUiComponent();
    }

    @Override
    public void setPresenter(QrCodeCompletedContract.Presenter presenter) {

        mQrCodeCompletedPresenter=checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static QrCodeCompletedFragment newInstance(){


        return new QrCodeCompletedFragment();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_qrcodecompleted_toolbar,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.item_qrcodecompleted_download:{

                ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView),IMAGE_QUALITY,ALPHA_PATH,CHARGE_PATH);
                SnackbarUtil.showSnackbar(mRoot,"Image saved",getContext());
                break;

            }

            case R.id.item_qrcodecompleted_share:{

                File file=ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenshotView),IMAGE_QUALITY,ALPHA_PATH,CHARGE_PATH);
                ScreenshotUtil.shareScreenshot(file,getContext());
                break;
            }

        }
        return true;
    }


    private void requestPermission() {


        int regEX = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (regEX != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(checkNotNull(getActivity()), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            SnackbarUtil.showSnackbar(mRoot, "Permission Ok", getContext());
        } else {

            SnackbarUtil.showSnackbar(mRoot, "Permission Failed", getContext());

        }
    }



    private void initUiComponent(){

        mScreenshotView=mRoot.findViewById(R.id.linearlayout_qrcodecompleted_mainview);
    }
}
