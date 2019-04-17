package de.netalic.falcon.ui.load;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.common.listcurrency.ListCurrencyActivity;
import de.netalic.falcon.common.spinneradapter.ListCurrencySpinnerAdapter;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.ui.util.DecimalDigitsInputFilter;
import de.netalic.falcon.ui.util.OffsetItemDecoration;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;
import static de.netalic.falcon.ui.addwallet.AddWalletFragment.SELECTED_CURRENCY;
import static de.netalic.falcon.ui.dashboard.DashboardFragment.SELECTED_WALLET;

public class LoadFragment extends Fragment implements LoadContract.View {

    private View mRoot;
    private RecyclerView mRecyclerViewPaymentGateway;
    private LoadPaymentGatewayRecyclerViewAdapter mRecyclerViewAdapterChargePaymentGateway;
    private LoadContract.Presenter mPresenter;
    private SnapHelper mPaymentGatewaySnapHelper;
    private List<Rate> mRateList;
    private ListCurrencySpinnerAdapter mListCurrencySpinnerAdapter;
    private Spinner mSpinnerCurrencyList;
    private DecimalFormat mDecimalFormat;
    private TextInputEditText mTextInputEditTextFirstAmount;
    private TextInputEditText mTextInputEditTextSecondAmount;
    private int mSelectedPosition;
    private Wallet mSelectedWallet;
    private TextInputLayout mTextInputLayoutFirstAmount;
    private Rate mRate;
    private double mSellRateCurrencySelectedWallet;
    private double mBuyRateCurrencySelectedWallet;
    private TextView mTextViewWalletType;
    private TextView mTextViewCurrencySymbol;
    private TextView mTextViewBalance;
    private TextView mTextViewExchangeTo;
    private Rate mSelectedCurrency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_load, null);
        mDecimalFormat = new DecimalFormat("#0.00");
        mSelectedWallet = getArguments().getParcelable(SELECTED_WALLET);
        return mRoot;
    }

    public static LoadFragment newInstance(Wallet selectedWallet) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(SELECTED_WALLET, selectedWallet);
        LoadFragment fragment = new LoadFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewPaymentGateway = mRoot.findViewById(R.id.recyclerViewPaymentGateway);
        mRecyclerViewPaymentGateway.addItemDecoration(new OffsetItemDecoration(getContext()));

        mRecyclerViewAdapterChargePaymentGateway = new LoadPaymentGatewayRecyclerViewAdapter(new ArrayList<>());
        mRecyclerViewPaymentGateway.setAdapter(mRecyclerViewAdapterChargePaymentGateway);
        mRecyclerViewPaymentGateway.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        mPaymentGatewaySnapHelper = new LinearSnapHelper();
        mPaymentGatewaySnapHelper.attachToRecyclerView(mRecyclerViewPaymentGateway);

        mTextInputLayoutFirstAmount.setHint(mSelectedWallet.getCurrencyCode());

        getWalletList();
        getListCurrency();
        getRate();
        setHasOptionsMenu(true);
        initListener();

        mTextViewWalletType.setText(mSelectedWallet.getCurrencyCode());
        mTextViewBalance.setText(String.valueOf(mSelectedWallet.getBalance()));
        mTextViewCurrencySymbol.setText(mSelectedWallet.getCurrencySymbol());

    }

    private double getSelectedRate(String currencyCode) {

        for (Rate rate : mRateList) {
            if (rate.getCurrencyCode().equals(currencyCode)) {
                mSellRateCurrencySelectedWallet = rate.getSell();
                mBuyRateCurrencySelectedWallet=rate.getBuy();
            }

        }

        return mSellRateCurrencySelectedWallet;
    }

    private void initListener() {

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

                    } else if (!mTextInputEditTextSecondAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) / mRateList.get(mSelectedPosition).getBuy() * mSellRateCurrencySelectedWallet))) {

                        if (mSelectedCurrency == null) {

                            mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()))));

                        } else {
                            mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()) * mBuyRateCurrencySelectedWallet/mSelectedCurrency.getBuy())));
                        }
                    }
                }

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

                    } else if (!mTextInputEditTextFirstAmount.getText().toString().equals(String.valueOf(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy() / mSellRateCurrencySelectedWallet))) {

                        if (mSelectedCurrency == null) {

                            mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()))));

                        } else {
                            mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.
                                    format(Double.valueOf(s.toString()) * mSelectedCurrency.getBuy()/mBuyRateCurrencySelectedWallet)));
                        }
                    }
                }
            }
        });

        mRecyclerViewPaymentGateway.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    View centerView = mPaymentGatewaySnapHelper.findSnapView(recyclerView.getLayoutManager());
                    int pos = recyclerView.getLayoutManager().getPosition(centerView);
                    ((LoadPaymentGatewayRecyclerViewAdapter) mRecyclerViewPaymentGateway.getAdapter()).select(pos);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });

        mTextViewExchangeTo.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ListCurrencyActivity.class);
            intent.putExtra(SELECTED_CURRENCY, mTextViewExchangeTo.getText().toString());
            startActivityForResult(intent, 1);
        });
    }

    private void initUiComponent() {

        mTextViewExchangeTo = mRoot.findViewById(R.id.textview_load_exchangeto);
        mTextInputEditTextFirstAmount = mRoot.findViewById(R.id.edittext_charge_firstamount);
        mTextInputEditTextSecondAmount = mRoot.findViewById(R.id.edittext_charge_secondeamount);
        mTextInputLayoutFirstAmount = mRoot.findViewById(R.id.textinputlayout_load_firstamount);
        mTextViewWalletType = mRoot.findViewById(R.id.textview_everywhereribbonheader_wallettype);
        mTextViewCurrencySymbol = mRoot.findViewById(R.id.textview_everywhereribbonheader_currencysymbol);
        mTextViewBalance = mRoot.findViewById(R.id.textview_everywhereribbonheader_walletbalance);
    }

    public void getWalletList() {

        mPresenter.getWalletList();
    }

    private void getListCurrency() {

        mPresenter.listExchangeRate();
    }

    @Override
    public void setRateList(List<Rate> rateList) {

        mRateList = rateList;
        getSelectedRate(mSelectedWallet.getCurrencyCode());
    }

    @Override
    public void setListWallet(List<Wallet> dataList) {

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.load_braintreelogo);
        mRecyclerViewAdapterChargePaymentGateway.setDataSource(list);
    }

    @Override
    public void setPresenter(LoadContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_everywhere_thathastick, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_everywhere_done: {

                Keyboard.hideKeyboard(mRoot);
                if (mTextInputEditTextFirstAmount.getText().toString().equals("")) {

                    checkNotNull(getContext());
                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_pleasefillbox), getContext());

                } else {

                    mPresenter.charge(mSelectedWallet.getId(), Double.parseDouble(mTextInputEditTextFirstAmount.getText().toString()), mRate.getVerifyRateId());
                }

                break;
            }
        }

        return true;
    }

    public void getRate() {

        mPresenter.exchangeRate(mSelectedWallet.getCurrencyCode());
    }

    @Override
    public void updateExchangeRateCurrency(Rate rate) {

        mRate = rate;

    }

    @Override
    public void showChargePaymentConfirmation(Transaction transaction) {

        Intent intent = new Intent(getContext(), LoadConfirmationActivity.class);
        intent.putExtra(SELECTED_WALLET, mSelectedWallet);
        intent.putExtra(LoadConfirmationActivity.ARGUMENT_CHARGE_START, transaction);
        startActivity(intent);
    }

    @Override
    public void showErrorInvalidAmount() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.load_invalidamount), getContext());
    }

    @Override
    public void showErrorInvalidWalletId() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.load_invalidwalletid), getContext());
    }

    @Override
    public void showErrorAmountIsSmallerThanLowerBound() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.load_amountissmallerthanlowerbound), getContext());
    }

    @Override
    public void showErrorAmountIsGreaterThanUpperBound() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.load_amountisgreaterthanupperbound), getContext());
    }

    @Override
    public void showErrorInvalidCurrency() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.load_invalidcurrency), getContext());
    }

    @Override
    public void showErrorRatesDoesNotExists() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.load_ratedosenotexists), getContext());
    }

    @Override
    public void showErrorChargeIsUnAvailable() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.load_chargeisunavailable),getContext());
    }

    @Override
    public void showErrorVerifyRateIsOutdatedOrItHasWrongCurrency() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.load_verifyrateisoutdatedorithaswrongcurrency),getContext());
    }

    @Override
    public void showErrorVerifyRateIdMissing() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.load_VerifyRateIdMissing),getContext());
    }

    @Override
    public void showErrorInvalidVerifyRateId() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.load_invalidverifyrateid),getContext());
    }

    @Override
    public void showErrorStartATransferAsAnAnonymous() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.load_startatransferasananonymous),getContext());
    }

    @Override
    public void internetConnectionError() {

        SnackbarUtil.showSnackbar(mRoot,getString(R.string.everywhere_connectionerror),getContext());
    }


    @Override
    public void onResume() {

        Rate currency = ((LoadActivity) getActivity()).getCurrency();
        mSelectedCurrency = currency;
        if (currency == null) {
            mTextViewExchangeTo.setText("");
        } else {
            mTextViewExchangeTo.setText(currency.getCurrencyCode());
        }
        super.onResume();
    }
}
