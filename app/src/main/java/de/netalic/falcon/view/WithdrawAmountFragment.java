package de.netalic.falcon.view;

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

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.WithdrawAmountSpinnerAdapter;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.WithdrawAmountContract;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawAmountFragment extends Fragment implements WithdrawAmountContract.View {

    private View mRoot;
    private WithdrawAmountContract.Presenter mPresenter;
    private EditText mEditTextWalletAmount;
    private EditText mEditTextBaseCurrency;
    private EditText mEditTextOtherCurrency;
    private TextView mTextViewUsemaximm;
    private TextView mTextViewUseMinimum;
    private Button mbuttonNextWithdraw;
    private static final String ARGUMENT_WALLET="WALLET";
    private Wallet mWallet;
    private Rate mRate;
    private List<Rate>mRateList;
    private Spinner mSpinnerCurrencyCode;
    private WithdrawAmountSpinnerAdapter mWithdrawAmountSpinnerAdapter;
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_withdrawamount,null);
        mWallet = getArguments().getParcelable(ARGUMENT_WALLET);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        mRate = new Rate("USD");
        getRate();
        getRateList();
        initListener();

    }

    @Override
    public void setPresenter(WithdrawAmountContract.Presenter presenter) {

        mPresenter=checkNotNull(presenter);

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

    private void initUiComponent(){

        mEditTextWalletAmount=mRoot.findViewById(R.id.edittext_withdrawamount_walletamount);
        mEditTextBaseCurrency=mRoot.findViewById(R.id.edittext_withdrawamount_basecurrency);
        mEditTextOtherCurrency=mRoot.findViewById(R.id.edittext_withdrawamount_othercurrency);
        mTextViewUsemaximm=mRoot.findViewById(R.id.textview_withdrawamount_usemaximum);
        mTextViewUseMinimum=mRoot.findViewById(R.id.textview_withdraw_useminimum);
        mbuttonNextWithdraw=mRoot.findViewById(R.id.button_withdrawamount_nextwithdraw);
        mSpinnerCurrencyCode=mRoot.findViewById(R.id.spinner_withdrawamount_currency);
    }

    @Override
    public void updateExchangeRateCurrency(Rate rate) {

        mRate=rate;
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

    public void getRate() {

        mPresenter.exchangeRate(mRate);
    }

    private void initListener(){

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
                    if (s.toString().equals("")) {
                        mEditTextBaseCurrency.setText("");
                        mEditTextOtherCurrency.setText("");

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rateOtherCurrencySell=mRateList.get(mPosition).getSell();
                        double rateUsdSell = mRate.getSell();
                        double otherCurrency=amountEnter*rateOtherCurrencySell;
                        double dollar = amountEnter * rateUsdSell;
                        double roundOtherCurrency=round(otherCurrency,2);
                        double roundDollar = round(dollar, 2);

                        mEditTextOtherCurrency.setText(String.valueOf(roundOtherCurrency));
                        mEditTextBaseCurrency.setText(String.valueOf(roundDollar));

                    }
                }
            }
        });

        mSpinnerCurrencyCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               double rateSell=mRateList.get(position).getSell();
               mPosition=position;

               if (!mEditTextWalletAmount.getText().toString().matches("")) {
                   double selectedCurrency = Double.parseDouble(mEditTextWalletAmount.getText().toString()) * rateSell;
                   double roundSelectedCurrency = round(selectedCurrency, 2);
                   mEditTextOtherCurrency.setText(String.valueOf(roundSelectedCurrency));
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    public void getRateList() {

        mPresenter.listExchangeRate();

    }

    @Override
    public void setRateList(List<Rate> rateList) {

        mRateList=rateList;
        mWithdrawAmountSpinnerAdapter = new WithdrawAmountSpinnerAdapter(getContext(), mRateList);
        mSpinnerCurrencyCode.setAdapter(mWithdrawAmountSpinnerAdapter);
    }

}
