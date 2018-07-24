package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.ChargePaymentGatewayRecyclerViewAdapter;
import de.netalic.falcon.adapter.OffsetItemDecoration;
import de.netalic.falcon.adapter.ChargeWalletRecyclerViewAdapter;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.ChargeContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFragment extends Fragment implements ChargeContract.View {

    private View mRoot;
    private RecyclerView mRecyclerViewWallets;
    private RecyclerView mRecyclerViewPaymentGateway;

    private ChargeWalletRecyclerViewAdapter mRecyclerViewAdapterChargeWallet;
    private ChargePaymentGatewayRecyclerViewAdapter mRecyclerViewAdapterChargePaymentGateway;

    private ChargeContract.Presenter mPresenter;
    private SnapHelper mWalletSnapHelper;
    private SnapHelper mPaymentGatewaySnapHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_charge, null);
        return mRoot;
    }

    public static ChargeFragment newInstance() {

        return new ChargeFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewWallets = mRoot.findViewById(R.id.recyclerViewWallets);
        mRecyclerViewPaymentGateway = mRoot.findViewById(R.id.recyclerViewPaymentGateway);

        mRecyclerViewAdapterChargeWallet = new ChargeWalletRecyclerViewAdapter(new ArrayList<>());
        mRecyclerViewWallets.setAdapter(mRecyclerViewAdapterChargeWallet);
        mRecyclerViewWallets.addItemDecoration(new OffsetItemDecoration(getContext()));
        mRecyclerViewWallets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        IndefinitePagerIndicator walletIndicator = mRoot.findViewById(R.id.charge_wallet_indicator);
        walletIndicator.attachToRecyclerView(mRecyclerViewWallets);

        mRecyclerViewAdapterChargePaymentGateway = new ChargePaymentGatewayRecyclerViewAdapter(new ArrayList<>());
        mRecyclerViewPaymentGateway.setAdapter(mRecyclerViewAdapterChargePaymentGateway);
        mRecyclerViewPaymentGateway.addItemDecoration(new OffsetItemDecoration(getContext()));
        mRecyclerViewPaymentGateway.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        IndefinitePagerIndicator payemntGatewayIndicator = mRoot.findViewById(R.id.charge_paymentgateway_indicator);
        payemntGatewayIndicator.attachToRecyclerView(mRecyclerViewPaymentGateway);

        mWalletSnapHelper = new PagerSnapHelper();
        mWalletSnapHelper.attachToRecyclerView(mRecyclerViewWallets);

        mPaymentGatewaySnapHelper = new PagerSnapHelper();
        mPaymentGatewaySnapHelper.attachToRecyclerView(mRecyclerViewPaymentGateway);

        initListener();
        getWalletList();
    }

    private void initListener() {

        mRecyclerViewWallets.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    View centerView = mPaymentGatewaySnapHelper.findSnapView(recyclerView.getLayoutManager());
                    int pos = recyclerView.getLayoutManager().getPosition(centerView);
                    ((ChargeWalletRecyclerViewAdapter) mRecyclerViewWallets.getAdapter()).select(pos);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });

        mRecyclerViewPaymentGateway.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    View centerView = mPaymentGatewaySnapHelper.findSnapView(recyclerView.getLayoutManager());
                    int pos = recyclerView.getLayoutManager().getPosition(centerView);
                    ((ChargePaymentGatewayRecyclerViewAdapter) mRecyclerViewPaymentGateway.getAdapter()).select(pos);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });


    }

    public void getWalletList() {

        mPresenter.getWalletList();
    }

    @Override
    public void setListWallet(List<Wallet> walletList) {

        walletList.add(walletList.get(0));
        walletList.add(walletList.get(0));
        walletList.add(walletList.get(0));

        mRecyclerViewAdapterChargeWallet.setDataSource(walletList);
        //TODO:(Ehsan) get it as an array from resource
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.charge_braintree);
        list.add(R.drawable.charge_braintree);
        list.add(R.drawable.charge_braintree);

        mRecyclerViewAdapterChargePaymentGateway.setDataSource(list);
    }

    @Override
    public void setPresenter(ChargeContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }
}
