package de.netalic.falcon.ui.withdraw;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.util.QrCodeUtil;
import nuesoft.helpdroid.crypto.CryptoUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawAmountFragment extends Fragment implements WithdrawAmountContract.View {

    private View mRoot;
    private WithdrawAmountContract.Presenter mPresenter;
    private EditText mEditTextWalletAmount;
    private EditText mEditTextBaseCurrency;
    private EditText mEditTextOtherCurrency;
    private TextView mTextViewUseMaximum;
    private TextView mTextViewUseMinimum;
    private Button mbuttonNextWithdraw;
    private static final String ARGUMENT_WALLET = "WALLET";
    private Wallet mWallet;
    private List<Rate> mRateList;
    private Spinner mSpinnerCurrencyCode;
    private WithdrawAmountSpinnerAdapter mWithdrawAmountSpinnerAdapter;
    private int mPosition;
    private double mRateUsdSell = -1;
    private DecimalFormat mDecimalFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_withdrawamount, null);
        mWallet = getArguments().getParcelable(ARGUMENT_WALLET);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        getRateList();
        initListener();
        mDecimalFormat = new DecimalFormat("0.00##");

    }

    @Override
    public void setPresenter(WithdrawAmountContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static WithdrawAmountFragment newInstance(Wallet wallet) {


        WithdrawAmountFragment fragment = new WithdrawAmountFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_WALLET, wallet);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent() {

        mEditTextWalletAmount = mRoot.findViewById(R.id.edittext_withdrawamount_walletamount);
        mEditTextBaseCurrency = mRoot.findViewById(R.id.edittext_withdrawamount_basecurrency);
        mEditTextOtherCurrency = mRoot.findViewById(R.id.edittext_withdrawamount_othercurrency);
        mTextViewUseMaximum = mRoot.findViewById(R.id.textview_withdrawamount_usemaximum);
        mTextViewUseMinimum = mRoot.findViewById(R.id.textview_withdraw_useminimum);
        mbuttonNextWithdraw = mRoot.findViewById(R.id.button_withdrawamount_nextwithdraw);
        mSpinnerCurrencyCode = mRoot.findViewById(R.id.spinner_withdrawamount_currency);
    }


    private void initListener() {

        mEditTextWalletAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mEditTextWalletAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {
                        mEditTextBaseCurrency.setText("");
                        mEditTextOtherCurrency.setText("");

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rateOtherCurrencySell = mRateList.get(mPosition).getSell();


                        for (Rate rate : mRateList) {
                            if (rate.getCurrencyCode().equals("USD")) {
                                mRateUsdSell = rate.getSell();
                            }

                        }

                        if (mRateUsdSell == -1) {
                            throw new IllegalStateException();
                        }

                        double otherCurrency = amountEnter * rateOtherCurrencySell;
                        double dollar = amountEnter * mRateUsdSell;
                        String roundOtherCurrency = mDecimalFormat.format(otherCurrency);
                        String roundDollar = mDecimalFormat.format(dollar);

                        mEditTextOtherCurrency.setText(String.valueOf(roundOtherCurrency));
                        mEditTextBaseCurrency.setText(String.valueOf(roundDollar));

                    }
                }
            }
        });


        mEditTextOtherCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mEditTextOtherCurrency.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {
                        mEditTextBaseCurrency.setText("");
                        mEditTextWalletAmount.setText("");

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rateOtherCurrencySell = mRateList.get(mPosition).getSell();


                        for (Rate rate : mRateList) {
                            if (rate.getCurrencyCode().equals("USD")) {
                                mRateUsdSell = rate.getSell();
                            }

                        }

                        if (mRateUsdSell == -1) {
                            throw new IllegalStateException();
                        }

                        double alpha = amountEnter / rateOtherCurrencySell;
                        double dollar = amountEnter * (mRateUsdSell / rateOtherCurrencySell);
                        String roundAlpha = mDecimalFormat.format(alpha);
                        String roundDollar = mDecimalFormat.format(dollar);

                        mEditTextWalletAmount.setText(roundAlpha);
                        mEditTextBaseCurrency.setText(roundDollar);

                    }
                }

            }
        });

        mEditTextBaseCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mEditTextBaseCurrency.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }

                    if (s.toString().equals("")) {
                        mEditTextOtherCurrency.setText("");
                        mEditTextWalletAmount.setText("");

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rateOtherCurrencySell = mRateList.get(mPosition).getSell();


                        for (Rate rate : mRateList) {
                            if (rate.getCurrencyCode().equals("USD")) {
                                mRateUsdSell = rate.getSell();
                            }

                        }

                        if (mRateUsdSell == -1) {
                            throw new IllegalStateException();
                        }

                        double alpha = amountEnter / mRateUsdSell;
                        double otherCurrency = amountEnter * (rateOtherCurrencySell / mRateUsdSell);
                        String roundAlpha = mDecimalFormat.format(alpha);
                        String roundOtherCurrency = mDecimalFormat.format(otherCurrency);

                        mEditTextWalletAmount.setText(roundAlpha);
                        mEditTextOtherCurrency.setText(roundOtherCurrency);

                    }
                }

            }
        });

        mSpinnerCurrencyCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                double rateSell = mRateList.get(position).getSell();
                mPosition = position;

                if (!mEditTextOtherCurrency.getText().toString().matches("")) {
                    double alpha = Double.parseDouble(mEditTextOtherCurrency.getText().toString()) / rateSell;
                    double dollar = Double.parseDouble(mEditTextOtherCurrency.getText().toString()) * (mRateUsdSell / rateSell);
                    String roundAlpha = mDecimalFormat.format(alpha);
                    String roundDollar = mDecimalFormat.format(dollar);
                    mEditTextBaseCurrency.setText(roundDollar);
                    mEditTextWalletAmount.setText(roundAlpha);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTextViewUseMinimum.setOnClickListener(v -> {

            mEditTextWalletAmount.requestFocus();
            mEditTextWalletAmount.setText("1");

        });

        mTextViewUseMaximum.setOnClickListener(v -> {

            mEditTextWalletAmount.requestFocus();
            mEditTextWalletAmount.setText("2500");

        });

        mbuttonNextWithdraw.setOnClickListener(v -> {

            Map<String, Object> map = new HashMap<>();
            int walletId = mWallet.getId();
            String amount = mEditTextWalletAmount.getText().toString();
            String wantedCurrencyCode = mRateList.get(mPosition).getCurrencyCode();
            String nonce = android.util.Base64.encodeToString(CryptoUtil.getSecureRandom(32), android.util.Base64.DEFAULT);
            long time = System.currentTimeMillis();

            map.put("walletId", walletId);
            map.put("amount", amount);
            map.put("wantedCurrencyCode", wantedCurrencyCode);
            map.put("nonce", nonce);
            map.put("time", time);
            mPresenter.generateQrCode(map);

        });
    }

    public void getRateList() {

        mPresenter.listExchangeRate();

    }

    @Override
    public void setRateList(List<Rate> rateList) {

        mRateList = rateList;
        mWithdrawAmountSpinnerAdapter = new WithdrawAmountSpinnerAdapter(getContext(), mRateList);
        mSpinnerCurrencyCode.setAdapter(mWithdrawAmountSpinnerAdapter);
    }

    @Override
    public void setQrCode(String qrCodeContent) {

        Bitmap bitmap = null;
        try {
            bitmap = QrCodeUtil.generateQrCode(qrCodeContent, 300, 300);
            navigateToWithdrawQrCompletedActivity(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            navigateToWithdrawQrFailedActivity();
        }
    }

    private void navigateToWithdrawQrCompletedActivity(Bitmap bitmap) {

        Intent intent = new Intent(getContext(), WithdrawQrCompletedActivity.class);
        intent.putExtra("qr", bitmap);
        startActivity(intent);
    }

    private void navigateToWithdrawQrFailedActivity() {

        Intent intent = new Intent(getContext(), WithdrawQrFailedActivity.class);
        startActivity(intent);
    }
}
