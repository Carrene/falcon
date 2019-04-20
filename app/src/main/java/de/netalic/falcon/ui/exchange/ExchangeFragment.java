package de.netalic.falcon.ui.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.common.listcurrency.ListCurrencyActivity;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.addwallet.AddWalletActivity;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.util.DecimalDigitsInputFilter;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;
import static de.netalic.falcon.ui.addwallet.AddWalletFragment.SELECTED_CURRENCY;

public class ExchangeFragment extends Fragment implements ExchangeContract.View {

    private View mViewRoot;
    private TextInputEditText mTextInputEditTextFirstAmount;
    private TextInputEditText mTextInputEditTextSecondAmount;
    private TextView mTextViewWalletSideUnit;
    private TextView mTextViewTargetSideUnit;
    private Wallet mWallet;
    private List<Rate> mRateList;
    private List<Wallet> mWalletList;
    private int mSelectedPosition;
    private double mWalletRate;
    private DecimalFormat mDecimalFormat;
    private ExchangeContract.Presenter mPresenter;
    public static final String WALLET = "wallet";
    private TextInputLayout mTextInputLayoutFirstAmount;
    public static final String ARGUMENT_TRANSACTION = "transaction";
    public static final String ARGUMENT_PAID_AMOUNT = "amount";
    private TextView mTextViewAddWalletAlert;
    private TextInputEditText mTextInputEditTextExchangeTo;
    private TextView mTextViewWalletType;
    private TextView mTextViewCurrencySymbol;
    private TextView mTextViewBalance;
    private Rate mSelectedCurrency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mViewRoot = inflater.inflate(R.layout.fragment_exchange, container, false);
        mWallet = getArguments().getParcelable(WALLET);
        mDecimalFormat = new DecimalFormat("#0.00");
        return mViewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initAlertUi();
        getListCurrency();
        initListener();

        mTextViewWalletType.setText(mWallet.getCurrencyCode());
        mTextViewBalance.setText(String.valueOf(mWallet.getBalance()));
        mTextViewCurrencySymbol.setText(mWallet.getCurrencySymbol());
    }

    private void initAlertUi() {

        mTextViewAddWalletAlert = new TextView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mTextViewAddWalletAlert.setLayoutParams(layoutParams);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_phoneconfirmation_toolbar, menu);
    }

    private void initUiComponents() {
        mTextInputEditTextFirstAmount = mViewRoot.findViewById(R.id.textinput_exchange_sourceamount);
        mTextInputEditTextSecondAmount = mViewRoot.findViewById(R.id.textinput_exchange_destinationamount);
        mTextInputLayoutFirstAmount = mViewRoot.findViewById(R.id.textinputlayout_exchange_firstamount);
        mTextViewWalletSideUnit = mViewRoot.findViewById(R.id.textview_exchange_walletsideunit);
        mTextViewTargetSideUnit = mViewRoot.findViewById(R.id.textview_exchange_targetsideunit);
        mTextInputEditTextExchangeTo = mViewRoot.findViewById(R.id.TextInputEditText_exchange_exchangeto);
        mTextViewWalletType = mViewRoot.findViewById(R.id.textview_everywhereribbonheader_wallettype);
        mTextViewCurrencySymbol = mViewRoot.findViewById(R.id.textview_everywhereribbonheader_currencysymbol);
        mTextViewBalance = mViewRoot.findViewById(R.id.textview_everywhereribbonheader_walletbalance);
        mTextViewWalletSideUnit.setText(mWallet.getCurrencySymbol() + "1 = ");
        mTextInputLayoutFirstAmount.setHint(mWallet.getCurrencyCode());
    }

    private void initListener() {

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

                    } else if (!mTextInputEditTextFirstAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy() / mWalletRate))) {


                        if (mSelectedCurrency == null) {

                            mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()))));

                        } else {
                            mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()) * mWalletRate / mSelectedCurrency.getSell())));
                        }
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

                    } else if (!mTextInputEditTextSecondAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) / mRateList.get(mSelectedPosition).getBuy() * mWalletRate))) {

                        if (mSelectedCurrency == null) {

                            mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()))));

                        } else {
                            mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()) * mSelectedCurrency.getSell() / mWalletRate)));
                        }
                    }
                }
            }
        });

        mTextInputEditTextExchangeTo.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ListCurrencyActivity.class);
            intent.putExtra(SELECTED_CURRENCY, mTextInputEditTextExchangeTo.getText().toString());
            startActivityForResult(intent, 1);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (mTextInputEditTextFirstAmount.getText().toString().equals("")) {

            SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.everywhere_pleasefillbox), getContext());
        } else {
            mPresenter.getWalletList();
        }

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
        setData();
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
    public void setListWallet(List<Wallet> model) {
        mWalletList = model;
        findDestinationWallet();
    }

    public void findDestinationWallet() {

        if (mSelectedCurrency != null) {
            String selectedCurrency = mSelectedCurrency.getCurrencyCode();
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
                AlertDialog dialog = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle).setView(mTextViewAddWalletAlert).
                        setMessage(getString(R.string.exchangefragment_alertmessage, selectedCurrency)).
                        setPositiveButton(R.string.positivebutton_exchangefragment, (dialogInterface, i) -> {
                            ((ViewGroup) mTextViewAddWalletAlert.getParent()).removeView(mTextViewAddWalletAlert);
                            navigationToAddWallet();
                        })
                        .setNegativeButton(R.string.negativebutton_exchangefragment, (dialogInterface, which) -> {
                            ((ViewGroup) mTextViewAddWalletAlert.getParent()).removeView(mTextViewAddWalletAlert);
                        }).show();

                TextView textView = dialog.findViewById(android.R.id.message);
                textView.setTextSize(18);
            }
        }
    }
    @Override
    public void navigationToExchangeConfirmation(Transaction body) {
        Intent intent = new Intent(getContext(), ExchangeConfirmationActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION, body);
        intent.putExtra(ARGUMENT_PAID_AMOUNT, (mWallet.getCurrencySymbol() + mTextInputEditTextFirstAmount.getText().toString()));
        startActivity(intent);
    }

    public void setWalletRate(Rate rate) {
        mWalletRate = rate.getSell();
    }

    private void setData() {
        for (Rate rate : mRateList) {
            if (rate.getCurrencyCode().equals(mWallet.getCurrencyCode())) {
                setWalletRate(rate);
                mRateList.remove(rate);
                break;
            }
        }
    }

    @Override
    public void showError700() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_invalidsourcewalletid), getContext());
    }

    @Override
    public void showError727() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_destinationwalletaddressisnotfound), getContext());
    }

    @Override
    public void showErrorSourceWalletNotFound404() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_sourcewalletnotfound), getContext());
    }

    @Override
    public void showError601() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_sourceanddestinationareequal), getContext());
    }

    @Override
    public void showErrorInvalidAmount702() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_invalidamount), getContext());
    }

    @Override
    public void showErrorAmountIsZero702() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_amountiszero), getContext());
    }

    @Override
    public void showErrorAmountIsNegative702() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_amountisnegative), getContext());
    }

    @Override
    public void showError600() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_insufficientBalance), getContext());

    }

    @Override
    public void showErrorTryingToTransferFromOtherWallet404() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_tryingtotransferfromanotherclientwallet), getContext());
    }

    @Override
    public void showError602() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_walletcurrenciesaredifferent), getContext());
    }

    @Override
    public void showError401() {

        SnackbarUtil.showSnackbar(mViewRoot, getContext().getString(R.string.transferpayee_starttransferasananonymous), getContext());

    }

    @Override
    public void internetConnectionError() {

        SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.everywhere_connectionerror), getContext());
    }

    @Override
    public void onResume() {

        Rate currency = ((ExchangeActivity) getActivity()).getCurrency();
        mSelectedCurrency = currency;

        if (currency == null) {
            mTextInputEditTextExchangeTo.setText("");
        } else {
            mTextInputEditTextExchangeTo.setText(currency.getCurrencyCode());
        }

        if (mSelectedCurrency == null) {


        } else {

            String targetSideAmount = String.valueOf(mSelectedCurrency.getCurrencySymbol()) + String.valueOf(mDecimalFormat.
                    format(Double.valueOf(mWalletRate / mSelectedCurrency.getBuy())));
            mTextViewTargetSideUnit.setText(targetSideAmount);
        }
        super.onResume();
    }
}