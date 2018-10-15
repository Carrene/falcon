package de.netalic.falcon.ui.charge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.ui.base.BaseActivity;

public class ListCurrencyFragment extends Fragment implements ListCurrencyContract.View,ListCurrencyRecyclerViewAdapter.Callback {
    private ListCurrencyContract.Presenter mListCurrencyPresenter;
    private View mRoot;
    private RecyclerView mRecyclerViewCurrencyList;
    private List<Currency> mCurrencyList;
    private ListCurrencyRecyclerViewAdapter mListCurrencyRecyclerViewAdapter;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_listcurrency, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        getCurrencyList();

        initListener();

    }


    @Override
    public void setPresenter(ListCurrencyContract.Presenter presenter) {

        mListCurrencyPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        ((BaseActivity) getActivity()).showMaterialDialog();

    }

    private void initListener() {


    }

    @Override
    public void dismissProgressBar() {


        ((BaseActivity) getActivity()).dismissMaterialDialog();
    }

    private void initUiComponent() {

        mRecyclerViewCurrencyList = mRoot.findViewById(R.id.recyclerview_listcurrency_listcurrency);



    }

    public static ListCurrencyFragment newInstance() {

        Bundle args = new Bundle();

        ListCurrencyFragment fragment = new ListCurrencyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setCurrencyList(List<Currency> currencyList) {

        mCurrencyList = currencyList;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCurrencyList.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        mRecyclerViewCurrencyList.addItemDecoration(dividerItemDecoration);

        mListCurrencyRecyclerViewAdapter = new ListCurrencyRecyclerViewAdapter(mCurrencyList,this);
        mRecyclerViewCurrencyList.setAdapter(mListCurrencyRecyclerViewAdapter);


    }

    private void getCurrencyList() {

        mListCurrencyPresenter.getCurrencyList();


    }


    @Override
    public void setCurrency(String currency) {

    }
}
