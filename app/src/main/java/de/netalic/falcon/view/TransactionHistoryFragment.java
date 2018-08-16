package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.TransactionHistoryRecyclerViewAdapter;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.TransactionHistoryContract;

public class TransactionHistoryFragment extends Fragment implements TransactionHistoryContract.View {

    private View mRoot;
    private TransactionHistoryContract.Presenter mTransactionHistoryPresenter;
    private List<Deposit>mDepositList;
    RecyclerView mRecyclerView;
    TransactionHistoryRecyclerViewAdapter mTransactionHistoryRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_transactionhistory,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        getDepositList();
    }

    @Override
    public void setPresenter(TransactionHistoryContract.Presenter presenter) {

        mTransactionHistoryPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

     public static TransactionHistoryFragment newInstance() {

        Bundle args = new Bundle();

        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setDepositList(List<Deposit> depositList) {

        mDepositList=depositList;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTransactionHistoryRecyclerViewAdapter = new TransactionHistoryRecyclerViewAdapter(mDepositList,getContext());
        mRecyclerView.setAdapter(mTransactionHistoryRecyclerViewAdapter);
    }
    private void getDepositList(){

        mTransactionHistoryPresenter.getDepositList();
    }

    private void initUiComponent(){

        mRecyclerView=mRoot.findViewById(R.id.recyclerview_transactionhistory_depositlist);
    }

}
