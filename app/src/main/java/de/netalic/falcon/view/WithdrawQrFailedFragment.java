package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.QrCodeFailedContract;

public class WithdrawQrFailedFragment extends Fragment implements QrCodeFailedContract.View {


    private QrCodeFailedContract.Presenter mQrCodeFailedPresenter;
    private View mRoot;
    private Button mButtonTryWithdraw;
    private Button mButtonDashboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_withdrawqrfailed, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initListener();
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

    public static WithdrawQrFailedFragment newInstance() {

        return new WithdrawQrFailedFragment();
    }

    private void initUiComponent() {

        mButtonTryWithdraw = mRoot.findViewById(R.id.button_qrcodefailed_trywithdraw);
        mButtonDashboard = mRoot.findViewById(R.id.button_qrcodefailed_dashborad);
    }

    private void initListener() {

        mButtonTryWithdraw.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), WithdrawActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        mButtonDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

    }
}
