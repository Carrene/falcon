package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeFailedContract;

public class QrCodeFailedFragment extends Fragment implements QrCodeFailedContract.View {


    private QrCodeFailedContract.Presenter mQrCodeFailedPresenter;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_qrcodefailed,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(QrCodeFailedContract.Presenter presenter) {

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static QrCodeFailedFragment newInstance(){

        return new QrCodeFailedFragment();
    }
}
