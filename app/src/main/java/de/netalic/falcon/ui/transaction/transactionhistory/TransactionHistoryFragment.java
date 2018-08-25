package de.netalic.falcon.ui.transaction.transactionhistory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.ui.base.HorizontalSpaceItemDecorationTransactionFilters;
import de.netalic.falcon.ui.base.HorizontalSpaceItemDecorationTransactionHistory;
import de.netalic.falcon.ui.transaction.transactionhistoryfilters.TransactionHistoryFiltersActivity;

public class TransactionHistoryFragment extends Fragment implements TransactionHistoryContract.View {

    private View mRoot;
    private TransactionHistoryContract.Presenter mTransactionHistoryPresenter;
    private List<Deposit> mDepositList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TransactionHistoryRecyclerViewAdapter mTransactionHistoryRecyclerViewAdapter;
    private int skip = 0;
    private int threshold = 10;
    private int take = threshold;

    private boolean isLoading = false;

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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//        getDepositList(sharedPreferences.getAll(), take, skip);
        setHasOptionsMenu(true);

    }

    @Override
    public void setPresenter(TransactionHistoryContract.Presenter presenter) {

        mTransactionHistoryPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransactionHistoryFragment newInstance() {

        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        return fragment;
    }

    @Override
    public void setDepositList(List<Deposit> depositList) {

        isLoading = false;
        skip += threshold;
        mTransactionHistoryRecyclerViewAdapter.setDataSource(depositList);

    }

    private void getDepositList(Map<String, ?> map, int take, int skip) {

        mTransactionHistoryPresenter.getDepositList(map, take, skip);
    }

    private void initUiComponent() {

        mRecyclerView = mRoot.findViewById(R.id.recyclerview_transactionhistory_depositlist);
        mRecyclerView.addItemDecoration(new HorizontalSpaceItemDecorationTransactionHistory(60));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mTransactionHistoryRecyclerViewAdapter = new TransactionHistoryRecyclerViewAdapter(mDepositList);
        mRecyclerView.setAdapter(mTransactionHistoryRecyclerViewAdapter);

        Paginate.with(mRecyclerView, callbacks)
                .setLoadingTriggerThreshold(11)
                .addLoadingListItem(true)
                .build();
    }

    Paginate.Callbacks callbacks = new Paginate.Callbacks() {
        @Override
        public void onLoadMore() {
            // Load next page of data (e.g. network or database)
            isLoading = true;
            getDepositList(null, take, skip);
        }

        @Override
        public boolean isLoading() {
            // Indicate whether new page loading is in progress or not
            return isLoading;
        }

        @Override
        public boolean hasLoadedAllItems() {
            // Indicate whether all data (pages) are loaded or not
            if (skip == 30) {
                return true;
            }
            return false;
        }
    };

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
