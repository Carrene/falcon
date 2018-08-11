package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.UsdCurrency;
import de.netalic.falcon.presenter.ChargeAmountContract;
import de.netalic.falcon.util.MaterialDialogUtil;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeAmountFragment extends Fragment implements ChargeAmountContract.View {

    private ChargeAmountContract.Presenter mChargePresenter;
    private View mRoot;
    private Button mButtonPayment;
    private EditText mEditTextAmountWallet;
    private EditText mEditTextAmountBase;
    private String mChargeDataToken;
    private Rate mRate;
    private Currency mUsd;


    public static final String ARGUMENT_WALLET_ID = "walletID";
    public static final String ARGUMENT_PAYMENT_GATEWAY_NAME = "paymentGatewayName";

    private String mPaymentGatewayName;
    private int mWalletId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_charge_amount, null);
        mPaymentGatewayName = getArguments().getString(ARGUMENT_PAYMENT_GATEWAY_NAME);
        mWalletId = getArguments().getInt(ARGUMENT_WALLET_ID);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initListener();
        setHasOptionsMenu(true);
        mRate = new Rate("USD");
        getRate();
    }

    public static ChargeAmountFragment newInstance(int walletId, String paymentGatewayName) {

        ChargeAmountFragment fragment = new ChargeAmountFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_WALLET_ID, walletId);
        bundle.putString(ARGUMENT_PAYMENT_GATEWAY_NAME, paymentGatewayName);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void setPresenter(ChargeAmountContract.Presenter presenter) {

        mChargePresenter = checkNotNull(presenter);
    }


    public void initUiComponent() {

        mButtonPayment = mRoot.findViewById(R.id.button_charge_payment);
        mEditTextAmountWallet = mRoot.findViewById(R.id.edittext_charge_amountwallet);
        mEditTextAmountBase = mRoot.findViewById(R.id.edittext_charge_amountbase);
    }


    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
        MaterialDialogUtil.getInstance().showMaterialDialog(getContext());
    }

    @Override
    public void dismissProgressBar() {

        MaterialDialogUtil.getInstance().dismissMaterialDialog();
    }

    @Override
    public void showChargePaymentConfirmation(Deposit deposit) {

        Intent intent = new Intent(getContext(), ChargeConfirmationActivity.class);
        intent.putExtra(ChargeConfirmationActivity.ARGUMENT_CHARGE_START, deposit);
        startActivity(intent);
    }

    @Override
    public void showErrorInvalidAmount() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_invalidamount), getContext());
    }

    @Override
    public void showErrorInvalidWalletId() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_invalidwalletid), getContext());
    }

    @Override
    public void showErrorAmountIsSmallerThanLowerBound() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_amountissmallerthanlowerbound), getContext());
    }

    @Override
    public void showErrorAmountIsGreaterThanUpperBound() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_amountisgreaterthanupperbound), getContext());
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

    public void initListener() {


        mEditTextAmountWallet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mEditTextAmountWallet.isFocused()) {
                    if (s.toString().equals("")) {
                        mEditTextAmountBase.setText("");

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rate = Double.parseDouble(mRate.getBuy());
                        double dollar = amountEnter * rate;
                        double roundDollar = round(dollar, 2);


                        mEditTextAmountBase.setText(String.valueOf(roundDollar));

                    }
                }
            }
        });

        mEditTextAmountBase.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mEditTextAmountBase.isFocused()) {
                    if (s.toString().equals("")) {
                        mEditTextAmountWallet.setText("");
                    } else {


                        double dollar = Double.parseDouble(s.toString());
                        double rate = Double.parseDouble(mRate.getBuy());
                        double alpha = dollar / rate;

                        mEditTextAmountWallet.setText(String.valueOf(alpha));

                    }
                }

            }
        });


        mButtonPayment.setOnClickListener(v -> {

                    Keyboard.hideKeyboard(mRoot);
                    if (mEditTextAmountWallet.getText().toString().equals("")) {

                        checkNotNull(getContext());
                        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_pleasefillamount), getContext());

                    } else {

                        mChargePresenter.charge(mWalletId, Double.parseDouble(mEditTextAmountWallet.getText().toString()));
                    }
                }
        );

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void getRate() {

        mChargePresenter.exchangeRate(mRate);
    }

    private double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
