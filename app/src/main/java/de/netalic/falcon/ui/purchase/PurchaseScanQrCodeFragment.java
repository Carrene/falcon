package de.netalic.falcon.ui.purchase;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.transfer.TransferConfirmationActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class PurchaseScanQrCodeFragment extends Fragment implements PurchaseScanQrCodeContract.View {

    private View mRoot;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private PurchaseScanQrCodeContract.Presenter mPurchaseScanQrCodePresenter;
    private static final int REQUEST_PERMISSIONS = 1;
    public static final String QR_CODE_RESULT="scanQRCode";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_purchasescanqrcode, null);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        requestCameraPermission();
        initListener();

    }

    @Override
    public void setPresenter(PurchaseScanQrCodeContract.Presenter presenter) {

        mPurchaseScanQrCodePresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static PurchaseScanQrCodeFragment newInstance() {

        PurchaseScanQrCodeFragment fragment = new PurchaseScanQrCodeFragment();
        return fragment;
    }

    private void initUiComponent() {

        mDecoratedBarcodeView = mRoot.findViewById(R.id.barcodeview_purchasescanqrcode_barcode);
        mDecoratedBarcodeView.setStatusText("");
        mDecoratedBarcodeView.setVisibility(View.VISIBLE);
    }


    private void requestCameraPermission() {

        int permission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.CAMERA);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
        } else {
            resumeScanner();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            checkNotNull(getContext());
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissionallowed), getContext());

        } else {

            checkNotNull(getContext());
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissiondenied), getContext());

        }
    }

    protected void resumeScanner() {

        int permission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.CAMERA);
        if (!mDecoratedBarcodeView.isActivated() && permission == PackageManager.PERMISSION_GRANTED) {
            mDecoratedBarcodeView.resume();
            mDecoratedBarcodeView.setVisibility(View.VISIBLE);
        }
    }

    private void initListener(){

        mDecoratedBarcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                if (result != null) {

                    Intent intent=new Intent(getContext(),TransferConfirmationActivity.class);
                    intent.putExtra(QR_CODE_RESULT,result.getText());
                    startActivity(intent);
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }
}
