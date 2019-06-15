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
import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.data.repository.authentication.AuthenticationRealmRepository;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.ui.addwallet.AddWalletActivity;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.exchange.ExchangeActivity;
import de.netalic.falcon.ui.load.LoadActivity;
import de.netalic.falcon.ui.receive.ReceiveActivity;
import de.netalic.falcon.ui.send.SendActivity;
import de.netalic.falcon.ui.transaction.transactionhistory.TransactionHistoryActivity;
import de.netalic.falcon.ui.withdraw.WithdrawActivity;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;
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

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor=new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        Map<String,Object>tokenBody= Parser.getTokenBody(sharedPreferencesJwtPersistor.get());

//        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).get(deal -> {
//
//            Authentication authentication=deal.getModel();
//            SnackbarUtil.showSnackbar(mViewRoot,authentication.getCredential(),getContext());
//        });

        RepositoryLocator.getInstance().getRepository(UserRepository.class).get((Integer) tokenBody.get("id"), deal -> {

            if (deal.getThrowable()==null){

                    SnackbarUtil.showSnackbar(mViewRoot,deal.getModel().getPhone(),getContext());
            } else {

            }

        });

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
