package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargeConfirmationContract;

public class ChargeConfirmationFragment extends Fragment implements ChargeConfirmationContract.View {

    private ChargeConfirmationContract.Presenter mChargeConfirmationPresenter;
    private View mRoot;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_chargeconfirmation,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(ChargeConfirmationContract.Presenter presenter) {
        mChargeConfirmationPresenter=presenter;
    }

    public static ChargeConfirmationFragment newInstance() {

        ChargeConfirmationFragment fragment = new ChargeConfirmationFragment();
        return fragment;
    }
}
