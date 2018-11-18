package de.netalic.falcon.ui.receive;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.zxing.WriterException;

import java.io.File;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Purchase;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.util.QrCodeUtil;
import de.netalic.falcon.util.ScreenshotUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReceiveFragment extends Fragment implements ReceiveContract.View {

    private View mRoot;
    private ReceiveContract.Presenter mReceivePresenter;
    private static final int REQUEST_PERMISSIONS = 1;
    private static final String ALPHA_PATH = "/Alpha";
    private static final String RECEIVE_PATH = "/Receive";
    private static final int IMAGE_QUALITY = 100;
    private View mScreenShotView;
    private TextInputEditText mTextInputEditTextAlphaAmount;
    private ImageView mImageViewGenerateQrCode;
    private String mWalletAddress;
    private Bitmap mBitmapQrCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_receive, null);
        mWalletAddress = getArguments().getString(DashboardFragment.WALLET_ADDRESS);
        return mRoot;
    }

    public static ReceiveFragment newInstance(String walletAddress) {

        Bundle bundle = new Bundle();
        bundle.putString(DashboardFragment.WALLET_ADDRESS, walletAddress);
        ReceiveFragment fragment = new ReceiveFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initUiComponent();
        initListener();
        generateQrCodeWithWalletAddress(mWalletAddress);
    }

    @Override
    public void setPresenter(ReceiveContract.Presenter presenter) {

        mReceivePresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_everywhere_sharedownloadtoolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_everywhere_share: {

                requestPermissionShare();

                break;
            }
            case R.id.item_everywhere_download: {

                requestPermissionSave();

                break;
            }

        }


        return true;
    }

    private void requestPermissionSave() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);

        } else {

            ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenShotView), IMAGE_QUALITY, ALPHA_PATH, RECEIVE_PATH);
            SnackbarUtil.showSnackbar(mRoot, getString(R.string.everywhere_imagesaved), getContext());
        }

    }

    private void requestPermissionShare() {

        int checkPermission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        } else {

            File file = new File(String.valueOf(ScreenshotUtil.saveScreenshot(ScreenshotUtil.takeScreenshot(mScreenShotView), IMAGE_QUALITY, ALPHA_PATH, RECEIVE_PATH)));
            ScreenshotUtil.shareScreenshot(file, getContext());

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

        mScreenShotView = mRoot.findViewById(R.id.relativelayout_receive_forscreenshot);
        mTextInputEditTextAlphaAmount = mRoot.findViewById(R.id.textinputedittext_receive_alphaamount);
        mImageViewGenerateQrCode = mRoot.findViewById(R.id.imageview_receive_createqrcode);

    }

    private void initListener() {


        mTextInputEditTextAlphaAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (s.toString().equals("")) {

                } else {
                    try {
                        Gson gson = new Gson();
                        Purchase purchase = new Purchase(Float.valueOf(s.toString()), mWalletAddress);
                        String purchaseJson = gson.toJson(purchase);

                        mBitmapQrCode = QrCodeUtil.generateQrCode(purchaseJson, 300, 300);
                        mImageViewGenerateQrCode.setImageBitmap(mBitmapQrCode);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void generateQrCodeWithWalletAddress(String walletAddress) {

        try {
            mBitmapQrCode = QrCodeUtil.generateQrCode(walletAddress, 300, 300);
            mImageViewGenerateQrCode.setImageBitmap(mBitmapQrCode);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }

}
