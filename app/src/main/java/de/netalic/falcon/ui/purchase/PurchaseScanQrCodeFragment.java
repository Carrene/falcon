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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Purchase;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.transfer.TransferConfirmationActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;
import static de.netalic.falcon.ui.transfer.TransferPayeeFragment.ARGUMENT_TRANSACTION;

public class PurchaseScanQrCodeFragment extends Fragment implements PurchaseScanQrCodeContract.View {

    private View mRoot;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private PurchaseScanQrCodeContract.Presenter mPurchaseScanQrCodePresenter;
    private static final int REQUEST_PERMISSIONS = 1;
    public static final String QR_CODE_RESULT = "scanQRCode";
    private int mWalletId;
    private long mLongLastSnackBarTime = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_purchasescanqrcode, null);
        setHasOptionsMenu(true);
        mWalletId = getArguments().getInt(PurchaseFragment.WALLET_ADDRESS);
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


        ((BaseActivity) getActivity()).showMaterialDialog();
    }

    @Override
    public void dismissProgressBar() {

        ((BaseActivity) getActivity()).dismissMaterialDialog();
    }


    public static PurchaseScanQrCodeFragment newInstance(int walletId) {

        Bundle bundle = new Bundle();
        bundle.putInt(PurchaseFragment.WALLET_ADDRESS, walletId);
        PurchaseScanQrCodeFragment fragment = new PurchaseScanQrCodeFragment();
        fragment.setArguments(bundle);
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

    private void initListener() {

        mDecoratedBarcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {

                if (result != null) {

                    mDecoratedBarcodeView.pause();

                    Gson gson = new Gson();
                    try {
                        Purchase purchase = gson.fromJson(result.getText(), Purchase.class);

                        mPurchaseScanQrCodePresenter.startTransfer(mWalletId, purchase.getWalletAddress(), purchase.getAmount());

                    } catch (JsonSyntaxException jsonSyntaxException) {

                        if ((System.currentTimeMillis() - mLongLastSnackBarTime) > 2) {

                            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.purchasescanqrcode_qrcodedoesnotmatch), getContext());
                            mDecoratedBarcodeView.resume();
                        } else {

                            mLongLastSnackBarTime = System.currentTimeMillis();
                        }
                    }

                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        });
    }

    @Override
    public void navigationToTransferConfirmation(Transaction transaction) {

        Intent intent = new Intent(getContext(), TransferConfirmationActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION, transaction);
        startActivity(intent);
    }

    @Override
    public void showError700() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_invalidsourcewalletid), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showError727() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_destinationwalletaddressisnotfound), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showErrorSourceWalletNotFound404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_sourcewalletnotfound), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showError601() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_sourceanddestinationareequal), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showErrorInvalidAmount702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_invalidamount), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showErrorAmountIsZero702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_amountiszero), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showErrorAmountIsNegative702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_amountisnegative), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showError600() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_insufficientBalance), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showErrorTryingToTransferFromOtherWallet404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_tryingtotransferfromanotherclientwallet), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showError602() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_walletcurrenciesaredifferent), getContext());
        mDecoratedBarcodeView.resume();

    }

    @Override
    public void showError401() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_starttransferasananonymous), getContext());
        mDecoratedBarcodeView.resume();

    }


}
