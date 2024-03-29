package de.netalic.falcon.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.addwallet.AddWalletActivity;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.exchange.ExchangeActivity;
import de.netalic.falcon.ui.load.LoadActivity;
import de.netalic.falcon.ui.receive.ReceiveActivity;
import de.netalic.falcon.ui.send.SendActivity;
import de.netalic.falcon.ui.transaction.transactionhistory.TransactionHistoryActivity;
import de.netalic.falcon.ui.withdraw.WithdrawActivity;
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
    private ImageView mImageViewTransaction;
    private ImageView mImageViewWithdrawIcon;
    private List<Wallet> mWalletList;
    public static final String SELECTED_WALLET = "wallet";

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
        mImageViewExchange = mViewRoot.findViewById(R.id.imageview_dashboard_exchange);
        mImageViewTransaction = mViewRoot.findViewById(R.id.imageview_dashboard_transactionicon);
        mImageViewWithdrawIcon = mViewRoot.findViewById(R.id.imageview_dashboard_withdrawicon);

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

            if (mWalletList != null && mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), SendActivity.class);
                intent.putExtra(SELECTED_WALLET, mWalletList.get(mSelectedWalletPosition));
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }

        });

        mImageViewReceive.setOnClickListener(v -> {
            if (mWalletList != null && mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), ReceiveActivity.class);
                intent.putExtra(SELECTED_WALLET, mWalletList.get(mSelectedWalletPosition));
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }
        });

        mImageViewCharge.setOnClickListener(v -> {

            if (mWalletList != null && mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), LoadActivity.class);
                intent.putExtra(SELECTED_WALLET, mWalletList.get(mSelectedWalletPosition));
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }

        });

        mImageViewExchange.setOnClickListener(v -> {
            if (mWalletList != null && mSelectedWalletPosition < mWalletList.size()) {
                Intent intent = new Intent(getActivity(), ExchangeActivity.class);
                intent.putExtra(SELECTED_WALLET, mWalletList.get(mSelectedWalletPosition));
                startActivity(intent);
            } else {
                SnackbarUtil.showSnackbar(mViewRoot, getString(R.string.dashboard_snackbarwalletnotselected), getContext());
            }
        });

        mImageViewTransaction.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), TransactionHistoryActivity.class);
            intent.putExtra(SELECTED_WALLET, mWalletList.get(mSelectedWalletPosition));
            startActivity(intent);
        });

        mImageViewWithdrawIcon.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), WithdrawActivity.class);
            startActivity(intent);
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
