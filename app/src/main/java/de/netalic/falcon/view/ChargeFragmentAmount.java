package de.netalic.falcon.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.google.gson.JsonObject;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.UsdCurrency;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.ChargeAmountContract;
import de.netalic.falcon.util.MaterialDialogUtil;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFragmentAmount extends Fragment implements ChargeAmountContract.View {

    private ChargeAmountContract.Presenter mChargePresenter;
    private View mRoot;
    private Spinner mSpinnerWalletList;
    public static final String ARGUMENT_USER = "USER";
    private Button mButtonPayment;
    private EditText mEditTextAmountWallet;
    private EditText mEditTextAmountBase;
    private static final int DROP_IN_REQUEST = 1;
    private String mChargeDataToken;
    private Rate mRate;
    private Currency mUsd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_charge_amount, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initListener();
        setHasOptionsMenu(true);
        mUsd = new UsdCurrency();
        mRate = new Rate(mUsd);
        getRate();
    }

    @Override
    public void setPresenter(ChargeAmountContract.Presenter presenter) {

        mChargePresenter = checkNotNull(presenter);
    }

    public static ChargeFragmentAmount newInstance() {

        ChargeFragmentAmount fragment = new ChargeFragmentAmount();
        return fragment;
    }

    public void initUiComponent() {

        mButtonPayment = mRoot.findViewById(R.id.button_charge_payment);
        mEditTextAmountWallet = mRoot.findViewById(R.id.edittext_charge_amountwallet);
        mEditTextAmountBase = mRoot.findViewById(R.id.edittext_charge_amountbase);
        mButtonPayment = mRoot.findViewById(R.id.button_charge_payment);
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
    public void setToken(JsonObject token) {

        String braintreeToken = token.get("braintreeToken").getAsString();
        mChargeDataToken = token.get("chargeDataToken").getAsString();

        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(braintreeToken);
        startActivityForResult(dropInRequest.getIntent(getContext()), DROP_IN_REQUEST);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_charge_toolbar, menu);
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

                        mEditTextAmountBase.setText(String.valueOf(dollar));

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

                        // mChargePresenter.charge(wallet.getId(), Double.parseDouble(mEditTextAmountWallet.getText().toString()));
                    }
                }
        );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String braintreeNonce = result.getPaymentMethodNonce().getNonce();
                mChargePresenter.finalize(10, braintreeNonce, mChargeDataToken);
                // send paymentMethodNonce to your server
                //TODO: finalize
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //TODO: Payment is cancelled
                // canceled
            } else {
                // an error occurred, checked the returned exception
                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    public void getRate() {

        mChargePresenter.exchangeRate(mRate);
    }
}
