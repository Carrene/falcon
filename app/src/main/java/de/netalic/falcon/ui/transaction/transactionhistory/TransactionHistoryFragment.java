package de.netalic.falcon.ui.transaction.transactionhistory;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.ui.transaction.transactionhistoryfilters.TransactionHistoryFiltersActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionHistoryFragment extends Fragment implements TransactionHistoryContract.View {

    private View mRoot;
    private TransactionHistoryContract.Presenter mTransactionHistoryPresenter;
    private TransactionHistoryRecyclerViewAdapter mTransactionHistoryRecyclerViewAdapter;
    private NoPaginate mNoPaginate;
    private Map<String, ?> mFilterMap;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewTransactionHistory;
    private TextView mTextViewWalletType;
    private TextView mTextViewCurrencySymbol;
    private TextView mTextViewBalance;
    private Wallet mSelectedWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transactionhistory, null);
        mSelectedWallet = getArguments().getParcelable(DashboardFragment.SELECTED_WALLET);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        mFilterMap = PreferenceManager.getDefaultSharedPreferences(getContext()).getAll();
        setHasOptionsMenu(true);
        mTextViewWalletType.setText(mSelectedWallet.getCurrencyCode());
        mTextViewBalance.setText(String.valueOf(mSelectedWallet.getBalance()));
        mTextViewCurrencySymbol.setText(mSelectedWallet.getCurrencySymbol());
    }

    @Override
    public void setPresenter(TransactionHistoryContract.Presenter presenter) {

        mTransactionHistoryPresenter = presenter;
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

        ((BaseActivity) getActivity()).dismissMaterialDialog();
    }

    public static TransactionHistoryFragment newInstance(Wallet selectedWallet) {

        Bundle bundle=new Bundle();
        bundle.putParcelable(DashboardFragment.SELECTED_WALLET,selectedWallet);
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setDepositList(List<Receipt> depositList) {

        mTransactionHistoryRecyclerViewAdapter.setDataSource(depositList);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showPaginationError(boolean show) {

        mNoPaginate.showError(show);
    }

    @Override
    public void showPaginationLoading(boolean loading) {

        mNoPaginate.showLoading(loading);
    }

    @Override
    public void loadNoMoreItem(boolean load) {

        mNoPaginate.setNoMoreItems(load);
    }

    private void getDepositList(Map<String, ?> map) {

        mTransactionHistoryPresenter.getDepositList(map);
    }

    private void initUiComponent() {

        mRecyclerViewTransactionHistory = mRoot.findViewById(R.id.recyclerview_transactionhistory);
        mSwipeRefreshLayout = mRoot.findViewById(R.id.swiperefresh_transactionhistory);
        mTextViewWalletType = mRoot.findViewById(R.id.textview_everywhereribbonheader_wallettype);
        mTextViewCurrencySymbol = mRoot.findViewById(R.id.textview_everywhereribbonheader_currencysymbol);
        mTextViewBalance = mRoot.findViewById(R.id.textview_everywhereribbonheader_walletbalance);
        mRecyclerViewTransactionHistory.setOnClickListener(v -> {

        });
        if (mNoPaginate != null) {
            mNoPaginate.unbind();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewTransactionHistory.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        mRecyclerViewTransactionHistory.addItemDecoration(dividerItemDecoration);

        mRecyclerViewTransactionHistory.setItemAnimator(new SlideInUpAnimator());

        mTransactionHistoryRecyclerViewAdapter = new TransactionHistoryRecyclerViewAdapter(getContext());
        mRecyclerViewTransactionHistory.setAdapter(mTransactionHistoryRecyclerViewAdapter);

        mNoPaginate = NoPaginate.with(mRecyclerViewTransactionHistory).setOnLoadMoreListener(() -> getDepositList(mFilterMap)).build();

        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mTransactionHistoryPresenter.resetPagination();
            mTransactionHistoryRecyclerViewAdapter.removeDataSource();
        });
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        mNoPaginate.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_transactionhistory_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_transactionhistory_filter: {

                Intent intent = new Intent(getContext(), TransactionHistoryFiltersActivity.class);
                startActivity(intent);
                break;
            }
        }
        return true;
    }
}