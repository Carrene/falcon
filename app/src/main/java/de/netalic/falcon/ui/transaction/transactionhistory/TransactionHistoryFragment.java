package de.netalic.falcon.ui.transaction.transactionhistory;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.ui.base.HorizontalSpaceItemDecorationTransactionHistory;
import de.netalic.falcon.ui.transaction.transactionhistoryfilters.TransactionHistoryFiltersActivity;

public class TransactionHistoryFragment extends Fragment implements TransactionHistoryContract.View {

    private View mRoot;
    private TransactionHistoryContract.Presenter mTransactionHistoryPresenter;
    private List<Deposit> mDepositList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TransactionHistoryRecyclerViewAdapter mTransactionHistoryRecyclerViewAdapter;

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
        getDepositList();
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

        mDepositList = depositList;
        mTransactionHistoryRecyclerViewAdapter.setDataSource(depositList);
    }

    private void getDepositList() {

        mTransactionHistoryPresenter.getDepositList();
    }

    private void initUiComponent() {

        mRecyclerView = mRoot.findViewById(R.id.recyclerview_transactionhistory_depositlist);
        mRecyclerView.addItemDecoration(new HorizontalSpaceItemDecorationTransactionHistory(60));

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(getContext(),layoutManager.getOrientation());


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mTransactionHistoryRecyclerViewAdapter = new TransactionHistoryRecyclerViewAdapter(mDepositList);
        mRecyclerView.setAdapter(mTransactionHistoryRecyclerViewAdapter);

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
