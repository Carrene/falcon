package de.netalic.falcon.ui.purchase;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Purchase;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.withdraw.WithdrawQrCompletedActivity;
import de.netalic.falcon.util.QrCodeUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class PurchaseAmountFragment extends Fragment implements PurchaseAmountContract.View {

    private PurchaseAmountContract.Presenter mPurchaseAmountPresenter;
    private View mRoot;
    private EditText mEditTextWalletAmount;
    private EditText mEditTextBaseCurrency;
    private TextView mTextViewUseMaximum;
    private TextView mTextViewUseMinimum;
    private Rate mRate;
    private DecimalFormat mDecimalFormat;
    private Button mButtonNextScan;
    private String mWalletAddress;
    private String mCurrencyCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_purchaseamount, null);
        mWalletAddress =getArguments().getString(PurchaseFragment.WALLET_ADDRESS);
        mCurrencyCode=getArguments().getString(PurchaseFragment.CURRENCY_CODE);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUiComponent();
        initListener();
        mRate = new Rate("USD");
        mDecimalFormat = new DecimalFormat("0.00##");
        getRate();
    }

    private void initUiComponent() {

        mEditTextWalletAmount = mRoot.findViewById(R.id.edittext_purchaseamount_amountwallet);
        mEditTextBaseCurrency = mRoot.findViewById(R.id.edittext_purchaseamount_amountbase);
        mTextViewUseMinimum = mRoot.findViewById(R.id.textview_purchaseamount_useminimum);
        mTextViewUseMaximum = mRoot.findViewById(R.id.textview_purchaseamount_usemaximum);
        mButtonNextScan =mRoot.findViewById(R.id.button_purchaseamount_payment);

    }

    public void getRate() {

        mPurchaseAmountPresenter.exchangeRate(mRate);
    }

    @Override
    public void setPresenter(PurchaseAmountContract.Presenter presenter) {
        mPurchaseAmountPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static PurchaseAmountFragment newInstance(String walletAddress,String currencyCode) {

        Bundle bundle = new Bundle();
        bundle.putString(PurchaseFragment.WALLET_ADDRESS,walletAddress);
        bundle.putString(PurchaseFragment.CURRENCY_CODE,currencyCode);

        PurchaseAmountFragment fragment = new PurchaseAmountFragment();
        fragment.setArguments(bundle);
        return fragment;
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

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rate = mRate.getBuy();
                        double dollar = amountEnter * rate;
                        String roundDollar = mDecimalFormat.format(dollar);
                        mEditTextBaseCurrency.setText(String.valueOf(roundDollar));

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
                        mEditTextWalletAmount.setText("");
                    } else {


                        double dollar = Double.parseDouble(s.toString());
                        double rate = mRate.getBuy();
                        double alpha = dollar / rate;
                        String roundAlpha = mDecimalFormat.format(alpha);
                        mEditTextWalletAmount.setText(roundAlpha);

                    }
                }

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

        mButtonNextScan.setOnClickListener(v -> {

            if (mEditTextWalletAmount.getText().toString().equals("")){

                checkNotNull(getContext());
                SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.everywhere_pleasefillbox),getContext());
            }
            else {

                Purchase purchase=new Purchase(Float.valueOf(mEditTextWalletAmount.getText().toString()),mWalletAddress,mCurrencyCode);
                mPurchaseAmountPresenter.generateQrCode(purchase);


            }
        });

    }

    @Override
    public void setQrCode(String qrCodeContent) {
        Bitmap bitmap = null;
        try {
            bitmap = QrCodeUtil.generateQrCode(qrCodeContent, 300, 300);
            navigateToPurchaseGenerateQrCode(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void showErrorInvalidCurrency() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeamount_invalidcurrency), getContext());
    }

    @Override
    public void showErrorRatesDoesNotExists() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeamount_ratedosenotexists), getContext());
    }

    @Override
    public void updateExchangeRateCurrency(Rate rate) {

        mRate = rate;
    }

    private void navigateToPurchaseGenerateQrCode(Bitmap bitmap) {

        Intent intent = new Intent(getContext(), WithdrawQrCompletedActivity.class);
        intent.putExtra("qr", bitmap);
        startActivity(intent);
    }
}
