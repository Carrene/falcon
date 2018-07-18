package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.SpinnerAdapter;
import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.UsdCurrency;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.DashboardContract;
import de.netalic.falcon.util.MaterialDialogUtil;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardContract.View {

    private DashboardContract.Presenter mPresenter;
    private View mRoot;
    private Spinner mSpinner;

    private static final String ARGUMENT_USER = "USER";
    private TextView mRateTextView;
    private Currency mUsd;
    private Rate mRate;
    private TextView mBalanceTextView;
    private List<Wallet> mWalletList;
    private SpinnerAdapter mSpinnerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_dashboard, null);
        setHasOptionsMenu(true);
        mUsd = new UsdCurrency();
        mRate = new Rate(mUsd);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        getRate();
        getWalletList();
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

        mRateTextView = mRoot.findViewById(R.id.textview_dashboard_ratecurrency);
        mSpinner = mRoot.findViewById(R.id.spinner_dashboard_spinner);
        mBalanceTextView = mRoot.findViewById(R.id.textview_dashboard_balance);
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
    public void updateExchangeRateCurrency(String rate) {

        mRateTextView.setText(String.valueOf(rate));
    }

    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
        MaterialDialogUtil.showMaterialDialog(getContext());
    }

    @Override
    public void dismissProgressBar() {

        MaterialDialogUtil.dismissMaterialDialog();
    }


    @Override
    public void setListWallet(List<Wallet> walletList) {

        mWalletList = walletList;
        mSpinnerAdapter = new SpinnerAdapter(getContext(), mWalletList);
        mSpinner.setAdapter(mSpinnerAdapter);
    }

    public void getRate() {

        mPresenter.exchangeRate(mRate);

    }

    public void getWalletList() {

        mPresenter.getWalletList();
    }

    public void initListener() {

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mBalanceTextView.setText(String.valueOf(mWalletList.get(position).getBalance()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
