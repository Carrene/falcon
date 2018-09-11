package de.netalic.falcon.ui.purchase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;

public class PurchaseAmountFragment extends Fragment implements PurchaseAmountContract.View {

    private View mRoot;
    private PurchaseAmountContract.Presenter mPurchaseAmountPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_purchaseamount,null);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(PurchaseAmountContract.Presenter presenter) {

        mPurchaseAmountPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static PurchaseAmountFragment newInstance() {

        PurchaseAmountFragment fragment = new PurchaseAmountFragment();
        return fragment;
    }
}
