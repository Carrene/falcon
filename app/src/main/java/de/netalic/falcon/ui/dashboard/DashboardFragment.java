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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.charge.AddWalletActivity;
import de.netalic.falcon.ui.charge.ChargeActivity;
import de.netalic.falcon.ui.exchange.ExchangeActivity;
import de.netalic.falcon.ui.receive.ReceiveActivity;
import de.netalic.falcon.ui.send.SendActivity;
import de.netalic.falcon.util.SnackbarUtil;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardContract.View, DashboardAdapter.Callback {

    private DashboardContract.Presenter mPresenter;
    private View mViewRoot;
    private DashboardAdapter mDashboardAdapter;
    private RecyclerView mRecyclerView;
    private SnapHelper mWalletSnapHelper;
    private int mSelectedWalletPosition;
    private ImageView mImageViewSend;
    private ImageView mImageViewReceive;
    private ImageView mImageViewCharge;
    private ImageView mImageViewExchange;
    private List<Wallet> mWalletList;
    public static final String WALLET_ID = "walletId";
    public static final String WALLET_ADDRESS = "walletAddress";

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

        mImageViewSend = mViewRoot.findViewById(R.id.imageview_dashboard_send);
        mRecyclerView = mViewRoot.findViewById(R.id.dashboard_recyclerview);
        mImageViewReceive = mViewRoot.findViewById(R.id.imageview_dashboard_receive);
        mImageViewCharge = mViewRoot.findViewById(R.id.imageview_dashboard_charge);
        mImageViewReceive = mViewRoot.findViewById(R.id.imageview_dashboard_receive);
        mImageViewExchange = mViewRoot.findViewById(R.id.imageview_dashboard_exchange);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {

                    private static final float SPEED = 1f;

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };

        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mDashboardAdapter = new DashboardAdapter(new ArrayList<>(), this);

        mRecyclerView.setAdapter(mDashboardAdapter);


        ScrollingPagerIndicator recyclerIndicator = mViewRoot.findViewById(R.id.indicator);
        recyclerIndicator.attachToRecyclerView(mRecyclerView);
        mWalletSnapHelper = new LinearSnapHelper();
        mWalletSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    public void initListener() {
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

        mImageViewSend.setOnClickListener(v -> {
            if (mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), SendActivity.class);
                intent.putExtra(WALLET_ID, mWalletList.get(mSelectedWalletPosition).getId());
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }
        });

        mImageViewReceive.setOnClickListener(v -> {
            if (mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), ReceiveActivity.class);
                intent.putExtra(WALLET_ADDRESS, mWalletList.get(mSelectedWalletPosition).getAddress());
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }
        });

        mImageViewCharge.setOnClickListener(v -> {
            if (mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), ChargeActivity.class);
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }

        });

        mImageViewExchange.setOnClickListener(v -> {
            if (mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), ExchangeActivity.class);
                intent.putExtra("wallet", mWalletList.get(mSelectedWalletPosition));
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }

        });

    }

    @Override
    public void setWalletList(List<Wallet> data) {
        mDashboardAdapter.setData(data);
        mWalletList = data;

    }

    @Override
    public void navigationToAddWallet() {
        Intent intent = new Intent(getContext(), AddWalletActivity.class);
        startActivity(intent);
    }
}