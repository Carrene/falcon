package de.netalic.falcon.ui.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.common.ListCurrencySpinnerAdapter;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.charge.AddWalletActivity;
import de.netalic.falcon.ui.send.SendConfirmationActivity;

import static com.google.common.base.Preconditions.checkNotNull;
import static de.netalic.falcon.ui.send.SendFragment.ARGUMENT_TRANSACTION;

public class ExchangeFragment extends Fragment implements ExchangeContract.View {

    private View mViewRoot;
    private TextInputEditText mTextInputEditTextFirstAmount;
    private TextInputEditText mTextInputEditTextSecondAmount;
    private Spinner mSpinnerCurrencyList;
    private Wallet mWallet;
    private List<Rate> mRateList;
    private List<Wallet> mWalletList;
    private int mSelectedPosition;
    private double mWalletRate;
    private DecimalFormat mDecimalFormat;
    private ListCurrencySpinnerAdapter mListCurrencySpinnerAdapter;
    private ExchangeContract.Presenter mPresenter;
    public static final String WALLET = "wallet";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewRoot = inflater.inflate(R.layout.fragment_exchange, container, false);
        mWallet = getArguments().getParcelable(WALLET);
        mDecimalFormat = new DecimalFormat("0.00##");
        return mViewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        getListCurrency();
        initListener();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_phoneconfirmation_toolbar, menu);
    }

    private void initUiComponents() {
        mTextInputEditTextFirstAmount = mViewRoot.findViewById(R.id.textinput_exchange_sourceamount);
        mTextInputEditTextSecondAmount = mViewRoot.findViewById(R.id.textinput_exchange_destinationamount);
        mSpinnerCurrencyList = mViewRoot.findViewById(R.id.spinner_exchange_spinner);

        mTextInputEditTextFirstAmount.setHint(mWallet.getCurrencyCode());
    }

    private void initListener() {

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

                    } else if (!mTextInputEditTextFirstAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy() / mWalletRate))) {
                        mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.
                                format(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy() / mWalletRate)));
                    }
                }
            }
        });


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

                    } else if (!mTextInputEditTextSecondAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) / mRateList.get(mSelectedPosition).getBuy() * mWalletRate))) {

                        mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                                format(Double.valueOf(s.toString()) / mRateList.get(mSelectedPosition).getBuy() * mWalletRate)));
                    }
                }
            }
        });

        mSpinnerCurrencyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mSelectedPosition = position;
                if (mTextInputEditTextSecondAmount.getText().toString().equals("") && mTextInputEditTextFirstAmount.getText().toString().equals("")) {


                } else if (mTextInputEditTextSecondAmount.getText().toString().equals("")) {
                    mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                            format(Double.valueOf(mTextInputEditTextFirstAmount.getText().toString()) / mRateList.get(mSelectedPosition).getBuy() * mWalletRate)));
                } else if (mTextInputEditTextFirstAmount.getText().toString().equals("")) {

                    mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.
                            format(Double.valueOf(mTextInputEditTextSecondAmount.getText().toString()) * mRateList.get(mSelectedPosition).getBuy() / mWalletRate)));
                } else {
                    mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                            format(Double.valueOf(mTextInputEditTextFirstAmount.getText().toString()) / mRateList.get(mSelectedPosition).getBuy() * mWalletRate)));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSelectedPosition = 1;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mPresenter.getWalletList();
        return true;
    }


    @Override
    public void setPresenter(ExchangeContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    private void getListCurrency() {

        mPresenter.listExchangeRate();
    }

    @Override
    public void setRateList(List<Rate> rateList) {

        mRateList = rateList;
        mListCurrencySpinnerAdapter = new ListCurrencySpinnerAdapter(getContext(), mRateList);
        mSpinnerCurrencyList.setAdapter(mListCurrencySpinnerAdapter);
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
    public void navigationToAddWallet() {

        Intent intent = new Intent(getContext(), AddWalletActivity.class);
        startActivity(intent);
    }

    public static ExchangeFragment newInstance(Wallet wallet) {
        Bundle args = new Bundle();
        args.putParcelable(WALLET, wallet);
        ExchangeFragment fragment = new ExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setWalletRate() {
        for (Rate rate : mRateList) {
            if (mWallet.getCurrencyCode().equals(rate.getCurrencyCode())) {
                mWalletRate = rate.getBuy();
            }
        }
    }

    @Override
    public void setListWallet(List<Wallet> model) {
        mWalletList = model;
        findDestinationWallet();
    }

    public void findDestinationWallet(){
        String selectedCurrency = mRateList.get(mSelectedPosition).getCurrencyCode();
        String destinationAddress = "";
        int walletId = 0;
        for (Wallet wallet : mWalletList) {
            if (wallet.getCurrencyCode().equals(selectedCurrency)) {
                destinationAddress = wallet.getAddress();
                walletId = mWallet.getId();
                mPresenter.transfer(walletId, destinationAddress, Float.valueOf(mTextInputEditTextSecondAmount.getText().toString()));
            }
        }
        if (destinationAddress.equals("")) {
            navigationToAddWallet();
        }
    }

    @Override
    public void navigationToExchangeConfirmation(Transaction body) {
        Intent intent = new Intent(getContext(), SendConfirmationActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION, body);
        startActivity(intent);
    }

}
