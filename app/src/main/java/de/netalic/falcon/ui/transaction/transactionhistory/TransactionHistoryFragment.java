package de.netalic.falcon.ui.transaction.transactionhistory;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.transaction.transactionhistoryfilters.TransactionHistoryFiltersActivity;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import ru.alexbykov.nopaginate.paginate.NoPaginate;

public class TransactionHistoryFragment extends Fragment implements TransactionHistoryContract.View {

    private View mRoot;
    private TransactionHistoryContract.Presenter mTransactionHistoryPresenter;
    private TransactionHistoryRecyclerViewAdapter mTransactionHistoryRecyclerViewAdapter;
    private NoPaginate mNoPaginate;
    private Map<String, ?> mFilterMap;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerViewTransactionHistory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transactionhistory, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        mFilterMap = PreferenceManager.getDefaultSharedPreferences(getContext()).getAll();
        setHasOptionsMenu(true);

    }

    @Override
    public void setPresenter(TransactionHistoryContract.Presenter presenter) {

        mTransactionHistoryPresenter = presenter;
    }


    @Override
    public void showProgressBar() {

        ((BaseActivity) getActivity()).showMaterialDialog();
    }

    @Override
    public void dismissProgressBar() {

        ((BaseActivity) getActivity()).dismissMaterialDialog();
    }


    public static TransactionHistoryFragment newInstance() {

        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
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


        mTransactionHistoryPresenter.getDepositList(map,((TransactionHistoryActivity)getActivity()).getStartDate(),((TransactionHistoryActivity) getActivity()).getEndDate());
    }

    private void initUiComponent() {

        mRecyclerViewTransactionHistory = mRoot.findViewById(R.id.recyclerview_transactionhistory);
        mSwipeRefreshLayout = mRoot.findViewById(R.id.swiperefresh_transactionhistory);
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