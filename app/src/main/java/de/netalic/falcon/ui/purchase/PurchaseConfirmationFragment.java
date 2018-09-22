package de.netalic.falcon.ui.purchase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;

public class PurchaseConfirmationFragment extends Fragment implements PurchaseConfirmationContract.View {

    private View mRoot;
    private PurchaseConfirmationContract.Presenter mPurchaseConfirmationPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_purchaseconfirmation, null);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(PurchaseConfirmationContract.Presenter presenter) {

        mPurchaseConfirmationPresenter = presenter;

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static PurchaseConfirmationFragment newInstance() {

        PurchaseConfirmationFragment fragment = new PurchaseConfirmationFragment();
        return fragment;
    }
}
