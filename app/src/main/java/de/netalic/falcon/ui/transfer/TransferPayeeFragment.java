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
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferPayeeFragment extends Fragment implements TransferPayeeContract.View {

    private TransferPayeeContract.Presenter mTransferPayeePresenter;
    private Button mButtonNextTransfer;
    private EditText mEditTextWalletAddress;
    private View mRoot;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    private static final int REQUEST_PERMISSIONS = 1;
    private Menu mMenu;
    public static final String ARGUMENT_TRANSFER_AMOUNT = "transferAmount";
    private int mSourceWalletAddress;
    private float mTransferAmount;
    public static final String ARGUMENT_TRANSACTION="transaction";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferpayee, null);
        checkNotNull(getArguments());
        mSourceWalletAddress = getArguments().getInt(TransferAmountFragment.ARGUMENT_WALLET_ADDRESS);
        mTransferAmount = getArguments().getFloat(ARGUMENT_TRANSFER_AMOUNT);
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
        initUiComponent();
        initListener();
    }

    @Override
    public void setPresenter(TransferPayeeContract.Presenter presenter) {

        mTransferPayeePresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMaterialDialog();

        }
    }

    @Override
    public void dismissProgressBar() {

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dismissMaterialDialog();
        }
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

            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissionallowed), getContext());
        } else {

            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissiondenied), getContext());
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
        mMenu.getItem(0).setIcon(R.drawable.transactionpayeeandpurchase_qropen);

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

    public static TransferPayeeFragment newInstance(int walletAddress, float transferAmount) {

        TransferPayeeFragment transferPayeeFragment = new TransferPayeeFragment();

        Bundle bundle = new Bundle();
        bundle.putFloat(ARGUMENT_TRANSFER_AMOUNT, transferAmount);
        bundle.putInt(TransferAmountFragment.ARGUMENT_WALLET_ADDRESS, walletAddress);
        transferPayeeFragment.setArguments(bundle);
        return transferPayeeFragment;
    }

    private void initListener() {

        mButtonNextTransfer.setOnClickListener(v -> {

            if (mEditTextWalletAddress.getText().toString().equals("")) {

                SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_pleasefillbox), getContext());
            } else {


                mTransferPayeePresenter.startTransfer(mSourceWalletAddress, mEditTextWalletAddress.getText().toString(), mTransferAmount);
            }

        });
    }

    private void initUiComponent() {

        mButtonNextTransfer = mRoot.findViewById(R.id.button_transferpayee_nexttransfer);
        mEditTextWalletAddress = mRoot.findViewById(R.id.edittext_transferpayee_walletaddress);
    }

    @Override
    public void navigationToTransferConfirmation(Transaction transaction) {

        Intent intent = new Intent(getContext(), TransferConfirmationActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION,transaction);
        startActivity(intent);
    }

    @Override
    public void showError700() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_invalidsourcewalletid), getContext());
    }

    @Override
    public void showError727() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_destinationwalletaddressisnotfound), getContext());
    }

    @Override
    public void showErrorSourceWalletNotFound404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_sourcewalletnotfound), getContext());
    }

    @Override
    public void showError601() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_sourceanddestinationareequal), getContext());
    }

    @Override
    public void showErrorInvalidAmount702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_invalidamount), getContext());
    }

    @Override
    public void showErrorAmountIsZero702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_amountiszero), getContext());
    }

    @Override
    public void showErrorAmountIsNegative702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_amountisnegative), getContext());
    }

    @Override
    public void showError600() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_insufficientBalance), getContext());

    }

    @Override
    public void showErrorTryingToTransferFromOtherWallet404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_tryingtotransferfromanotherclientwallet), getContext());
    }

    @Override
    public void showError602() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_walletcurrenciesaredifferent), getContext());
    }

    @Override
    public void showError401() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferpayee_starttransferasananonymous), getContext());

    }
}
