package de.netalic.falcon.ui.purchase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;

public class PurchaseFragment extends Fragment implements PurchaseContract.View {


    private PurchaseContract.Presenter mPurchasePresenter;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_purchase,null);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(PurchaseContract.Presenter presenter) {

        mPurchasePresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static PurchaseFragment newInstance() {

        PurchaseFragment fragment = new PurchaseFragment();
        return fragment;
    }
}
