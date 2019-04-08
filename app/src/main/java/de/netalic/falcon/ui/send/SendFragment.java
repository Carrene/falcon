package de.netalic.falcon.ui.send;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.text.DecimalFormat;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.common.ListCurrencySpinnerAdapter;
import de.netalic.falcon.data.model.Purchase;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.ui.util.DecimalDigitsInputFilter;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class SendFragment extends Fragment implements SendContract.View {

    private View mRoot;
    private SendContract.Presenter mSendPresenter;
    private TextInputEditText mTextInputEditTextWalletAddress;
    private DecoratedBarcodeView mDecoratedBarcodeView;
    public static final String ARGUMENT_TRANSACTION = "transaction";
    private static final int REQUEST_PERMISSION = 1;
    private long mLongLastSnackBarTime = 0;
    private List<Rate> mRateList;
    private ListCurrencySpinnerAdapter mListCurrencySpinnerAdapter;
    private double mRateCurrencySelectedWallet;
    private Spinner mSpinnerCurrencyList;
    private Wallet mSelectedWallet;
    private DecimalFormat mDecimalFormat;
    private TextInputEditText mTextInputEditTextFirstAmount;
    private TextInputEditText mTextInputEditTextSecondAmount;
    private int mSelectedPosition;
    private TextInputLayout mTextInputLayoutFirstAmount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_send, null);
        mDecimalFormat = new DecimalFormat("#0.00");
        mSelectedWallet = getArguments().getParcelable(DashboardFragment.SELECTED_WALLET);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        getListCurrency();
        setHasOptionsMenu(true);
        requestCameraPermission();
        initListener();
        mTextInputLayoutFirstAmount.setHint(mSelectedWallet.getCurrencyCode());

    }


    private void getListCurrency() {

        mSendPresenter.listExchangeRate();
    }

    @Override
    public void setPresenter(SendContract.Presenter presenter) {

        mSendPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
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

    public static SendFragment newInstance(Wallet selectedWallet) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(DashboardFragment.SELECTED_WALLET, selectedWallet);
        SendFragment fragment = new SendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent() {

        mSpinnerCurrencyList = mRoot.findViewById(R.id.spinner_send_spinner);
        mTextInputEditTextFirstAmount = mRoot.findViewById(R.id.edittext_send_firstamount);
        mTextInputEditTextSecondAmount = mRoot.findViewById(R.id.edittext_send_secondeamount);
        mTextInputLayoutFirstAmount = mRoot.findViewById(R.id.textinputlayout_send_firstamount);
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

                if (mTextInputEditTextFirstAmount.getText().toString().equals("")) {

                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_pleasefillbox), getContext());
                } else {

                    mSendPresenter.startTransfer(mSelectedWallet.getId(), mTextInputEditTextWalletAddress.getText().toString(), Float.valueOf(mTextInputEditTextFirstAmount.getText().toString()));
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

                        mTextInputEditTextFirstAmount.setText(String.valueOf(purchase.getAmount()));
                        mTextInputEditTextWalletAddress.setText(purchase.getWalletAddress());
                        mDecoratedBarcodeView.resume();
                    } catch (JsonSyntaxException e) {

                        if (System.currentTimeMillis() - mLongLastSnackBarTime > 2) {

                            SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_qrcodedoesnotmatch), getContext());
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


        mSpinnerCurrencyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mSelectedPosition = position;
                if (mTextInputEditTextSecondAmount.getText().toString().equals("") && mTextInputEditTextFirstAmount.getText().toString().equals("")) {


                } else if (mTextInputEditTextSecondAmount.getText().toString().equals("")) {
                    mTextInputEditTextSecondAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextFirstAmount.getText().toString()) * (mRateCurrencySelectedWallet/mRateList.get(position).getBuy())))));
                } else if (mTextInputEditTextFirstAmount.getText().toString().equals("")) {

                    mTextInputEditTextFirstAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextSecondAmount.getText().toString()) * (mRateList.get(position).getBuy()/mRateCurrencySelectedWallet)))));
                } else {

                    mTextInputEditTextSecondAmount.clearComposingText();
                    mTextInputEditTextSecondAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextFirstAmount.getText().toString()) * (mRateCurrencySelectedWallet/mRateList.get(position).getBuy())))));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mSelectedPosition = 0;
            }
        });

        mTextInputEditTextSecondAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        mTextInputEditTextSecondAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mTextInputEditTextSecondAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {

                        mTextInputEditTextFirstAmount.setText("");

                    } else if (!mTextInputEditTextFirstAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy() / mRateCurrencySelectedWallet))) {
                        mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.
                                format(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy() / mRateCurrencySelectedWallet)));
                    }
                }
            }
        });

        mTextInputEditTextFirstAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        mTextInputEditTextFirstAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mTextInputEditTextFirstAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {

                        s.clear();
                    }

                    if (s.toString().equals("")) {

                        mTextInputEditTextSecondAmount.setText("");

                    } else if (!mTextInputEditTextSecondAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) / mRateList.get(mSelectedPosition).getBuy() * mRateCurrencySelectedWallet))) {

                        mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                                format(Double.valueOf(s.toString()) / mRateList.get(mSelectedPosition).getBuy() * mRateCurrencySelectedWallet)));
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {

        mDecoratedBarcodeView.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mDecoratedBarcodeView.pause();
        super.onPause();
    }

    @Override
    public void onStop() {

        mDecoratedBarcodeView.pause();
        super.onStop();
    }

    @Override
    public void navigationToSendConfirmation(Transaction transaction) {

        Intent intent = new Intent(getContext(), SendConfirmationActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION, transaction);
        startActivity(intent);
    }

    @Override
    public void showError700() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_invalidsourcewalletid), getContext());
    }

    @Override
    public void showError727() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_destinationwalletaddressisnotfound), getContext());
    }

    @Override
    public void showErrorSourceWalletNotFound404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_sourcewalletnotfound), getContext());
    }

    @Override
    public void showError601() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_sourceanddestinationareequal), getContext());
    }

    @Override
    public void showErrorInvalidAmount702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_invalidamount), getContext());
    }

    @Override
    public void showErrorAmountIsZero702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_amountiszero), getContext());
    }

    @Override
    public void showErrorAmountIsNegative702() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_amountisnegative), getContext());
    }

    @Override
    public void showError600() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_insufficientBalance), getContext());

    }

    @Override
    public void showErrorTryingToTransferFromOtherWallet404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_tryingtotransferfromanotherclientwallet), getContext());
    }

    @Override
    public void showError602() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_walletcurrenciesaredifferent), getContext());
    }

    @Override
    public void showError401() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.send_starttransferasananonymous), getContext());

    }

    private double getSelectedRate(String currencyCode) {

        for (Rate rate : mRateList) {
            if (rate.getCurrencyCode().equals(currencyCode)) {
                mRateCurrencySelectedWallet = rate.getSell();
            }

        }

        return mRateCurrencySelectedWallet;
    }

    @Override
    public void setRateList(List<Rate> rateList) {
        mRateList = rateList;
        mListCurrencySpinnerAdapter = new ListCurrencySpinnerAdapter(getContext(), mRateList);
        mSpinnerCurrencyList.setAdapter(mListCurrencySpinnerAdapter);
        getSelectedRate(mSelectedWallet.getCurrencyCode());
    }

    @Override
    public void internetConnectionError() {

        SnackbarUtil.showSnackbar(mRoot,getString(R.string.everywhere_connectionerror),getContext());
    }


}
