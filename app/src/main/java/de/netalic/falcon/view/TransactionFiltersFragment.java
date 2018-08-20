package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.TransactionFiltersContract;

public class TransactionFiltersFragment extends Fragment implements TransactionFiltersContract.View {

    private TransactionFiltersContract.Presenter mTransactionFiltersPresenter;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inflater.inflate(R.layout.fragment_transactionfilters,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(TransactionFiltersContract.Presenter presenter) {

        mTransactionFiltersPresenter=presenter;

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransactionFiltersFragment newInstance() {

        Bundle args = new Bundle();

        TransactionFiltersFragment fragment = new TransactionFiltersFragment();
        fragment.setArguments(args);
        return fragment;
    }


}
