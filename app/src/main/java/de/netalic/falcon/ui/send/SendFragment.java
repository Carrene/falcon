package de.netalic.falcon.ui.send;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class SendFragment extends Fragment implements SendContract.View {

    private View mRoot;
    private SendContract.Presenter mSendPresenter;
    private TextInputEditText mEditTextAlphaAmount;
    private EditText mEditTextEveryCurrencyAmount;
    private TextInputEditText mTextInputEditTextWalletAddress;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    public static final String ARGUMENT_TRANSACTION = "transaction";
    private static final int REQUEST_PERMISSION = 1;
    private long mLongLastSnackBarTime = 0;
    private int mSourceWalletId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_send, null);
        mSourceWalletId=getArguments().getInt(DashboardFragment.WALLET_ADDRESS);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        setHasOptionsMenu(true);
        requestCameraPermission();
        initListener();

    }

    @Override
    public void setPresenter(SendContract.Presenter presenter) {

        mSendPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static SendFragment newInstance(int walletId) {

        Bundle bundle = new Bundle();
        bundle.putInt(DashboardFragment.WALLET_ADDRESS,walletId);
        SendFragment fragment = new SendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent() {

        mEditTextAlphaAmount = mRoot.findViewById(R.id.textinputedittext_send_alphaamount);
        mEditTextEveryCurrencyAmount = mRoot.findViewById(R.id.edittext_send_everycurrencyamount);
        mTextInputEditTextWalletAddress = mRoot.findViewById(R.id.textinputedittext_send_walletaddress);
        mDecoratedBarcodeView = mRoot.findViewById(R.id.decoratedbarcodeview_send_scanqrcode);
        mDecoratedBarcodeView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_everywhere_thathastick, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_everywhere_done: {

                if (mEditTextAlphaAmount.getText().toString().equals("")) {

                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_pleasefillbox), getContext());
                } else {


                    mSendPresenter.startTransfer(mSourceWalletId, mTextInputEditTextWalletAddress.getText().toString(), Float.valueOf(mEditTextAlphaAmount.getText().toString()));

                }

            }
            break;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            checkNotNull(getContext());
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissionallowed), getContext());

        } else {

            checkNotNull(getContext());
            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_permissiondenied), getContext());

        }
    }

    private void requestCameraPermission() {

        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION);
        } else {

            resumeScanner();
        }

    }

    private void resumeScanner() {

        int permission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA);
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

                        mEditTextAlphaAmount.setText(String.valueOf(purchase.getAmount()));
                        mTextInputEditTextWalletAddress.setText(purchase.getWalletAddress());
                        mDecoratedBarcodeView.resume();
                    } catch (JsonSyntaxException e) {

                        if (System.currentTimeMillis() - mLongLastSnackBarTime > 2) {

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
    public void onResume() {
        super.onResume();
        mDecoratedBarcodeView.resume();
    }

    @Override
    public void navigationToSendConfirmation(Transaction transaction) {

        Intent intent = new Intent(getContext(), SendConfirmationActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION, transaction);
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
