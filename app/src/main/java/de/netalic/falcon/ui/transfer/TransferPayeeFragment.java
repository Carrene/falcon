package de.netalic.falcon.ui.transfer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.transaction.transactionhistoryfilters.TransactionHistoryFiltersActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferPayeeFragment extends Fragment implements TransferPayeeContract.View {

    private TransferPayeeContract.Presenter mTransferPayeePresenter;
    private View mRoot;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private EditText mEditTextWalletAddress;
    private static final int REQUEST_PERMISSIONS = 1;
    private Menu mMenu;

    public static TransferPayeeFragment newInstance() {

        return new TransferPayeeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferpayee, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mDecoratedBarcodeView = mRoot.findViewById(R.id.dbv_barcode);
        mEditTextWalletAddress = mRoot.findViewById(R.id.edittext_transferpayee_walletaddress);
        mDecoratedBarcodeView.setStatusText("");
        initListeners();
        mDecoratedBarcodeView.setVisibility(View.INVISIBLE);
        setHasOptionsMenu(true);

    }

    private void initListeners() {

        mDecoratedBarcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                if (result != null) {
                    mEditTextWalletAddress.setText(result.getText());
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    @Override
    public void setPresenter(TransferPayeeContract.Presenter presenter) {

        mTransferPayeePresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

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

            SnackbarUtil.showSnackbar(mRoot, "Permission Allowed", getContext());
        } else {

            SnackbarUtil.showSnackbar(mRoot, "Permission Denied", getContext());
        }
    }

    protected void resumeScanner() {

        int permission = ContextCompat.checkSelfPermission(checkNotNull(getContext()), Manifest.permission.CAMERA);
        if (!mDecoratedBarcodeView.isActivated() && permission == PackageManager.PERMISSION_GRANTED) {
            mDecoratedBarcodeView.resume();
            mDecoratedBarcodeView.setVisibility(View.VISIBLE);
            if (mMenu != null) {
                mMenu.getItem(0).setIcon(R.drawable.transactionpayee_qrclose);
            }
        }


    }

    protected void pauseScanner() {

        mDecoratedBarcodeView.pause();
        mDecoratedBarcodeView.setVisibility(View.INVISIBLE);
        mMenu.getItem(0).setIcon(R.drawable.transactionpayee_qropen);

    }

    private void handleQr() {

        if (mDecoratedBarcodeView.getVisibility() == View.VISIBLE) {
            pauseScanner();
            mDecoratedBarcodeView.setVisibility(View.INVISIBLE);
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
        pauseScanner();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_transferpayee_toolbar, menu);
        mMenu = menu;
        resumeScanner();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_transferpayee_qr: {
                handleQr();
                break;
            }
        }
        return true;
    }
}
