package de.netalic.falcon.common.listcurrency;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.ui.addwallet.AddWalletFragment;
import de.netalic.falcon.ui.base.BaseActivity;

public class ListCurrencyFragment extends Fragment implements ListCurrencyContract.View, ListCurrencyRecyclerViewAdapter.Callback {
    private ListCurrencyContract.Presenter mListCurrencyPresenter;
    private View mRoot;
    private RecyclerView mRecyclerViewCurrencyList;
    private List<Rate> mRateList;
    private ListCurrencyRecyclerViewAdapter mListCurrencyRecyclerViewAdapter;
    private Rate mSelectedCurrency;
    private Callback mCallback;


    public interface Callback {

        void setCurrency(Rate currency);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_listcurrency, null);
        mSelectedCurrency = getArguments().getParcelable(AddWalletFragment.SELECTED_CURRENCY);
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

    public static ListCurrencyFragment newInstance(Rate selectedCurrency) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(AddWalletFragment.SELECTED_CURRENCY, selectedCurrency);
        ListCurrencyFragment fragment = new ListCurrencyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void setCurrencyList(List<Rate> currencyList) {

        mRateList = currencyList;

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCurrencyList.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), layoutManager.getOrientation());
        mRecyclerViewCurrencyList.addItemDecoration(dividerItemDecoration);

        if (mSelectedCurrency == null) {
            mListCurrencyRecyclerViewAdapter = new ListCurrencyRecyclerViewAdapter(mRateList, "", this);
            mRecyclerViewCurrencyList.setAdapter(mListCurrencyRecyclerViewAdapter);

        } else {
            mListCurrencyRecyclerViewAdapter = new ListCurrencyRecyclerViewAdapter(mRateList, mSelectedCurrency.getCurrencyCode(), this);
            mRecyclerViewCurrencyList.setAdapter(mListCurrencyRecyclerViewAdapter);
        }
    }

    private void getCurrencyList() {

        mListCurrencyPresenter.getCurrencyList();
    }

    @Override
    public void setCurrency(Rate currency) {

        mSelectedCurrency = currency;
        mListCurrencyRecyclerViewAdapter = new ListCurrencyRecyclerViewAdapter(mRateList, mSelectedCurrency.getCurrencyCode(), this);
        mRecyclerViewCurrencyList.setAdapter(mListCurrencyRecyclerViewAdapter);
        mListCurrencyRecyclerViewAdapter.notifyDataSetChanged();
        mCallback.setCurrency(currency);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) context;
    }


}
