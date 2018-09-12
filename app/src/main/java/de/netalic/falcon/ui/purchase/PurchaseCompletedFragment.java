package de.netalic.falcon.ui.purchase;

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
import de.netalic.falcon.ui.dashboard.DashboardActivity;

public class PurchaseCompletedFragment extends Fragment implements PurchaseCompletedContract.View {

    private View mRoot;
    private Button mButtonNavigationToDashboard;
    private Button mButtonPurchaseAgain;
    private PurchaseCompletedContract.Presenter mPurchaseCompletedPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_purchasecompleted,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUiComponent();
        initListener();
    }

    @Override
    public void setPresenter(PurchaseCompletedContract.Presenter presenter) {

        mPurchaseCompletedPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static PurchaseCompletedFragment newInstance() {

        PurchaseCompletedFragment fragment = new PurchaseCompletedFragment();
        return fragment;
    }

    private void initUiComponent(){

        mButtonNavigationToDashboard=mRoot.findViewById(R.id.button_purchasecompleted_dashborad);
        mButtonPurchaseAgain=mRoot.findViewById(R.id.button_purchasecompleted_purchaseagain);
    }

    private void initListener(){

        mButtonNavigationToDashboard.setOnClickListener(v -> {

            Intent intent=new Intent(getContext(), DashboardActivity.class);
            startActivity(intent);

        });

        mButtonPurchaseAgain.setOnClickListener(v -> {



        });
    }
}
