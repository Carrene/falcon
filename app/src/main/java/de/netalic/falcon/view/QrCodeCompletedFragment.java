package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeCompletedContract;
import static com.google.common.base.Preconditions.checkNotNull;


public class QrCodeCompletedFragment extends Fragment implements QrCodeCompletedContract.View {


    private QrCodeCompletedContract.Presenter mQrCodeCompletedPresenter;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_qrcodecompleted,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(QrCodeCompletedContract.Presenter presenter) {

        mQrCodeCompletedPresenter=checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static QrCodeCompletedFragment newInstance(){


        return new QrCodeCompletedFragment();
    }
}
