package de.netalic.falcon.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Currency;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.DashboardContract;
import de.netalic.falcon.util.ActivityUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardContract.View {

    private DashboardContract.Presenter mPresenter;
    private View mRoot;
    private TextView mPhoneNumber;
    private TextView mEmail;
    private User mUser;
    private static final String ARGUMENT_USER = "USER";
    private TextView mRate;
    private Currency USD;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        USD = new Currency(1, "usd", 2);
        mRoot = inflater.inflate(R.layout.fragment_dashboard, null);
        mUser = getArguments().getParcelable(ARGUMENT_USER);
        mProgressDialog=new ProgressDialog(getActivity());
        setHasOptionsMenu(true);
        initUiComponents();
//        setPhoneNumber();
//        setEmail();
        getRate();
        return mRoot;
    }

    public static DashboardFragment newInstance(User user) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_USER, user);
        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setArguments(bundle);
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

        mPhoneNumber = mRoot.findViewById(R.id.textview_dashboard_phonenumbernavigationheader);
        mEmail = mRoot.findViewById(R.id.textview_dashboard_emailnavigationheader);
        mRate = mRoot.findViewById(R.id.textview_dashboard_ratecurrency);

    }


    @Override
    public void setEmail() {

    }

    @Override
    public void setPhoneNumber() {

    }

    @Override
    public void errorForNullCurrency() {

        Snackbar.make(mRoot, getContext().getString(R.string.dashboard_yourcurrencyisnull), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void updateExchangeRateCurrency(double rate) {

        mRate.setText(String.valueOf(rate));
    }

    @Override
    public void showProgressBar() {

        ActivityUtil.showProgressBar(mProgressDialog);
    }

    @Override
    public void disMissShowProgressBar() {

        ActivityUtil.disMissShowProgressBar(mProgressDialog);
    }

    public void getRate() {

        mPresenter.exchangeRate(USD);

    }
}
