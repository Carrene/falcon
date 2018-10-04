package de.netalic.falcon.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.purchase.PurchaseActivity;
import de.netalic.falcon.ui.withdraw.WithdrawActivity;

import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardContract.View {

    private DashboardContract.Presenter mPresenter;
    private View mRoot;
    private Spinner mSpinnerWalletList;
    private TextView mTextViewEquivalentToBaseCurrency;
    private Currency mUsd;
    private Rate mRate;
    private TextView mTextViewBalance;
    private List<Wallet> mWalletList;
    private DashboardWalletSpinnerAdapter mDashboardWalletSpinnerAdapter;
    private ImageView mImageViewWithdraw;
    private ImageView mImageViewPurchase;
    private DecimalFormat mDecimalFormat;
    private List<Rate> mRateList;
    private double mRateIrrSell;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_dashboard, null);
        setHasOptionsMenu(true);
        mRate = new Rate("USD");
        mDecimalFormat = new DecimalFormat("0.00##");
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        getRate();
        getWalletList();
        getRatesList();
        initListener();

    }

    public static DashboardFragment newInstance() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        return dashboardFragment;
    }

    @Override
    public void onResume() {

        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_dashboard_toolbar, menu);
    }

    public void initUiComponents() {

        mTextViewEquivalentToBaseCurrency = mRoot.findViewById(R.id.textview_dashboard_ratecurrency);
        mSpinnerWalletList = mRoot.findViewById(R.id.spinner_dashboard_spinner);
        mTextViewBalance = mRoot.findViewById(R.id.textview_dashboard_balance);
        mImageViewWithdraw = mRoot.findViewById(R.id.imageview_dashboard_withdraw);
        mImageViewPurchase = mRoot.findViewById(R.id.imageview_dashboard_purchase);
    }

    private void getRatesList() {

        mPresenter.getListRates();
    }

    @Override
    public void showErrorInvalidCurrency() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.dashboard_invalidcurrency), getContext());
    }

    @Override
    public void showErrorRatesDoesNotExists() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.dashboard_ratedosenotexists), getContext());
    }

    @Override
    public void updateExchangeRateCurrency(Rate rate) {

        mRate = rate;
        mTextViewEquivalentToBaseCurrency.setText(String.valueOf(rate));
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
    public void setListWallet(List<Wallet> walletList) {

        mWalletList = walletList;
        mDashboardWalletSpinnerAdapter = new DashboardWalletSpinnerAdapter(getContext(), mWalletList);
        mSpinnerWalletList.setAdapter(mDashboardWalletSpinnerAdapter);
    }

    @Override
    public void setListRates(List<Rate> listRates) {

        mRateList = listRates;

    }

    public void getRate() {

        mPresenter.exchangeRate(mRate);

    }

    public void getWalletList() {

        mPresenter.getWalletList();
    }

    public void initListener() {

        mSpinnerWalletList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String roundDollar = getContext().getString(R.string.dashboard_sorryicantcalculatenow);
                mTextViewBalance.setText(String.valueOf(Double.valueOf(mWalletList.get(position).getBalance()).longValue()));


                for (Rate rate : mRateList) {
                    if (rate.getCurrencyCode().equals("IRR")) {
                        mRateIrrSell = rate.getSell();
                    }

                }


                if (mWalletList.get(position).getCurrencyCode().equals("ALP")) {
                    roundDollar = mDecimalFormat.format(mWalletList.get(position).getBalance() * mRate.getSell());
                } else if (mWalletList.get(position).getCurrencyCode().equals("USD")) {
                    roundDollar = mDecimalFormat.format(mWalletList.get(position).getBalance());

                } else if (mWalletList.get(position).getCurrencyCode().equals("IRR")) {
                    roundDollar = mDecimalFormat.format(mWalletList.get(position).getBalance() * (mRate.getSell() / mRateIrrSell));

                }

                mTextViewEquivalentToBaseCurrency.setText(getContext().getString(R.string.everywhere_dollarsymbol) + " " + roundDollar);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mImageViewWithdraw.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), WithdrawActivity.class);
            startActivity(intent);
        });

        mImageViewPurchase.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), PurchaseActivity.class);
            startActivity(intent);

        });
    }

}
