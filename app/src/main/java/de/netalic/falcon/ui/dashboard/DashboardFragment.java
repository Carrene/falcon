package de.netalic.falcon.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardContract.View {

    private DashboardContract.Presenter mPresenter;
    private View mViewRoot;
    private DashboardAdapter mDashboardAdapter;
    private List<Wallet> mData;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewRoot = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mPresenter.getWalletList();
        return mViewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponents();

    }

    public static DashboardFragment newInstance() {

        DashboardFragment dashboardFragment = new DashboardFragment();
        return dashboardFragment;
    }

    @Override
    public void setPresenter(DashboardContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMaterialDialog();
        }
    }

    @Override
    public void dismissProgressBar() {

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dismissMaterialDialog();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    public void initUiComponents() {

    }

    @Override
    public void setAdapter(List<Wallet> data){
        mData = data;
        mRecyclerView = mViewRoot.findViewById(R.id.dashboard_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDashboardAdapter = new DashboardAdapter(mData);
        mRecyclerView.setAdapter(mDashboardAdapter);
    }


    @Override
    public void setWalletList(List<Wallet> body) {
        mData = body;
    }
}