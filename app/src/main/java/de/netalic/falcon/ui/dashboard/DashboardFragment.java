package de.netalic.falcon.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.charge.AddWalletActivity;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardContract.View, DashboardAdapter.Callback {

    private DashboardContract.Presenter mPresenter;
    private View mViewRoot;
    private DashboardAdapter mDashboardAdapter;
    private RecyclerView mRecyclerView;
    private SnapHelper mWalletSnapHelper;
    private int mSelectedWalletPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewRoot = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mPresenter.getWalletList();
        setHasOptionsMenu(true);
        return mViewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initListener();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard_toolbar, menu);
    }


    public void initUiComponents() {
        mRecyclerView = mViewRoot.findViewById(R.id.dashboard_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext()) {
//
//            @Override
//            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
//                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
//
//                    private static final float SPEED = 300f;
//
//                    @Override
//                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                        return SPEED / displayMetrics.densityDpi;
//                    }
//
//                };
//                smoothScroller.setTargetPosition(position);
//                startSmoothScroll(smoothScroller);
//            }
//
//        };

        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mDashboardAdapter = new DashboardAdapter(new ArrayList<>(), this);

        mRecyclerView.setAdapter(mDashboardAdapter);


        ScrollingPagerIndicator recyclerIndicator = mViewRoot.findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(mRecyclerView);
        mWalletSnapHelper = new LinearSnapHelper();
        mWalletSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    public void initListener(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    View centerView = mWalletSnapHelper.findSnapView(recyclerView.getLayoutManager());
                    mSelectedWalletPosition = recyclerView.getLayoutManager().getPosition(centerView);
                    ((DashboardAdapter) mRecyclerView.getAdapter()).select(mSelectedWalletPosition);
                }
            }

        });
    }

    @Override
    public void setWalletList(List<Wallet> data){
    mDashboardAdapter.setData(data);

    }

    @Override
    public void navigationToAddWallet() {
        Intent intent = new Intent(getContext(), AddWalletActivity.class);
        startActivity(intent);
    }
}