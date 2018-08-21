package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.State;
import de.netalic.falcon.ui.base.HorizontalSpaceItemDecoration;

public class TransactionHistoryFiltersFragment extends Fragment implements TransactionHistoryFiltersContract.View {

    private TransactionHistoryFiltersContract.Presenter mTransactionFiltersPresenter;
    private RecyclerView mTransactionFilterRecyclerView;
    private TransactionHistoryFilterRecyclerViewAdapter mTransactionHistoryFilterRecyclerViewAdapter;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transactionfilters, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();

    }

    private void initUiComponent() {

        List<State> stateList = new ArrayList<>();

        stateList.add(new State("", "", "", "Success"));
        stateList.add(new State("", "", "", "Failed"));

        stateList.add(new State("", "", "", "Withdraw"));
        stateList.add(new State("", "", "", "Charge"));
        stateList.add(new State("", "", "", "Receive"));
        stateList.add(new State("", "", "", "Purchase"));

        stateList.add(new State("", "", "", "Last day"));
        stateList.add(new State("", "", "", "Last week"));


        List<String> headerList = Arrays.asList(getContext().getResources().getStringArray(R.array.transactionfilter_headers));

        mTransactionFilterRecyclerView = mRoot.findViewById(R.id.recyclerview_transactionfilters_filtering);
        mTransactionFilterRecyclerView.addItemDecoration(new HorizontalSpaceItemDecoration(10));

        mTransactionFilterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTransactionHistoryFilterRecyclerViewAdapter = new TransactionHistoryFilterRecyclerViewAdapter(stateList, headerList);
        mTransactionFilterRecyclerView.setAdapter(mTransactionHistoryFilterRecyclerViewAdapter);
    }

    @Override
    public void setPresenter(TransactionHistoryFiltersContract.Presenter presenter) {

        mTransactionFiltersPresenter = presenter;

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransactionHistoryFiltersFragment newInstance() {

        TransactionHistoryFiltersFragment fragment = new TransactionHistoryFiltersFragment();
        return fragment;
    }


}
