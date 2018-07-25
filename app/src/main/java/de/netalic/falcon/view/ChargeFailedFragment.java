package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.ChargeFailedContract;
import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFailedFragment extends Fragment implements ChargeFailedContract.View {

    private ChargeFailedContract.Presenter mChargeFailedPresenter;
    private View mRoot;
    @Override
    public void setPresenter(ChargeFailedContract.Presenter presenter) {

        mChargeFailedPresenter=checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRoot=inflater.inflate(R.layout.fragment_chargefailed,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static ChargeFailedFragment newInstance() {

        ChargeFailedFragment fragment = new ChargeFailedFragment();
        return fragment;
    }
}
