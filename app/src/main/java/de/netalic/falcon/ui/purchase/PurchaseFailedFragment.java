package de.netalic.falcon.ui.purchase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;

public class PurchaseFailedFragment extends Fragment implements PurchaseFailedContract.View {

    private View mRoot;
    private PurchaseFailedContract.Presenter  mPurchaseFailedPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_purchasefailed,null);
        return mRoot;
    }

    @Override
    public void setPresenter(PurchaseFailedContract.Presenter presenter) {

        mPurchaseFailedPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static PurchaseFailedFragment newInstance() {

        PurchaseFailedFragment fragment = new PurchaseFailedFragment();
        return fragment;
    }
}
