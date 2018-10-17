package de.netalic.falcon.ui.charge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DecimalFormat;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeAmountFragment extends Fragment implements ChargeAmountContract.View {

    private ChargeAmountContract.Presenter mChargePresenter;
    private View mRoot;
    private Button mButtonPayment;
    private EditText mEditTextAmountWallet;
    private EditText mEditTextAmountBase;
    private TextInputLayout mTextInputLayoutChargeAmount;
    private TextInputLayout mTextInputLayoutPaidAmount;
    private String mChargeDataToken;
    private Rate mRate;
    private Currency mUsd;
    private DecimalFormat mDecimalFormat;
    public static final String ARGUMENT_WALLET_ID = "walletID";
    public static final String ARGUMENT_PAYMENT_GATEWAY_NAME = "paymentGatewayName";
    private String mPaymentGatewayName;
    private int mWalletId;
    private User mUser;
    private String mBaseCurrency;

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
        mDecimalFormat = new DecimalFormat("0.00##");
        getBaseCurrency();
        getRate();
        mTextInputLayoutPaidAmount.setHint(mUser.getBaseCurrencyCode());
    }

    public static ChargeAmountFragment newInstance(int walletId, String paymentGatewayName) {

        ChargeAmountFragment fragment = new ChargeAmountFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARGUMENT_WALLET_ID, walletId);
        bundle.putString(ARGUMENT_PAYMENT_GATEWAY_NAME, paymentGatewayName);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void getBaseCurrency(){

            mChargePresenter.getBaseCurrency();
}

    @Override
    public void setPresenter(ChargeAmountContract.Presenter presenter) {

        mChargePresenter = checkNotNull(presenter);
    }


    public void initUiComponent() {

        mButtonPayment = mRoot.findViewById(R.id.button_charge_payment);
        mEditTextAmountWallet = mRoot.findViewById(R.id.edittext_charge_amountwallet);
        mEditTextAmountBase = mRoot.findViewById(R.id.edittext_charge_basecurrency);
        mTextInputLayoutChargeAmount=mRoot.findViewById(R.id.textinputlayout_chargeamount_alpha);
        mTextInputLayoutPaidAmount=mRoot.findViewById(R.id.textinputlayout_chargeamount_basecurrency);
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

    @Override
    public void showChargePaymentConfirmation(Transaction transaction) {

        Intent intent = new Intent(getContext(), ChargeConfirmationActivity.class);
        intent.putExtra(ChargeConfirmationActivity.ARGUMENT_CHARGE_START, transaction);
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
    public void showErrorChargeIsUnAvailable() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.chargeamount_chargeisunavailable),getContext());
    }

    @Override
    public void showErrorVerifyRateIsOutdatedOrItHasWrongCurrency() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.chargeamount_verifyrateisoutdatedorithaswrongcurrency),getContext());
    }

    @Override
    public void showErrorVerifyRateIdMissing() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.chargeamount_VerifyRateIdMissing),getContext());
    }

    @Override
    public void showErrorInvalidVerifyRateId() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.chargeamount_invalidverifyrateid),getContext());
    }

    @Override
    public void showErrorStartATransferAsAnAnonymous() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.chargeamount_startatransferasananonymous),getContext());
    }

    @Override
    public void updateExchangeRateCurrency(Rate rate) {

        mRate = rate;

    }

    @Override
    public void setBaseCurrency(User user) {

        mUser=user;
        mBaseCurrency=mUser.getBaseCurrencyCode();

    }

    @Override
    public void setBaseCurrencyNotSet() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.dashboard_basecurrencydoesnotset), getContext());
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

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {
                        mEditTextAmountBase.setText("");

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rate = mRate.getBuy();
                        double dollar = amountEnter * rate;
                        String roundDollar = mDecimalFormat.format(dollar);
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

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {
                        mEditTextAmountWallet.setText("");
                    } else {


                        double dollar = Double.parseDouble(s.toString());
                        double rate = mRate.getBuy();
                        double alpha = dollar / rate;
                        String roundAlpha = mDecimalFormat.format(alpha);
                        mEditTextAmountWallet.setText(roundAlpha);

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

                        mChargePresenter.charge(mWalletId, Double.parseDouble(mEditTextAmountWallet.getText().toString()),mRate.getVerifyRateId());
                    }
                }
        );

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
    public void getRate() {

        mChargePresenter.exchangeRate(mUser.getBaseCurrencyCode());
    }

}
