package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.ChargePaymentGatewayRecyclerViewAdapter;
import de.netalic.falcon.adapter.ChargeWalletRecyclerViewAdapter;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.ChargeContract;
import de.netalic.falcon.util.MaterialDialogUtil;

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

    private Button mButtonChargeNext;
    private int mSelectedWalletPosition;
    private List<Wallet> mWalletList;

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
        mButtonChargeNext = mRoot.findViewById(R.id.button_charge_next);

        mRecyclerViewAdapterChargeWallet = new ChargeWalletRecyclerViewAdapter(new ArrayList<>());
        mRecyclerViewWallets.setAdapter(mRecyclerViewAdapterChargeWallet);
        mRecyclerViewWallets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        IndefinitePagerIndicator walletIndicator = mRoot.findViewById(R.id.charge_wallet_indicator);
        walletIndicator.attachToRecyclerView(mRecyclerViewWallets);

        mRecyclerViewAdapterChargePaymentGateway = new ChargePaymentGatewayRecyclerViewAdapter(new ArrayList<>());
        mRecyclerViewPaymentGateway.setAdapter(mRecyclerViewAdapterChargePaymentGateway);
        mRecyclerViewPaymentGateway.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        IndefinitePagerIndicator paymentGatewayIndicator = mRoot.findViewById(R.id.charge_paymentgateway_indicator);
        paymentGatewayIndicator.attachToRecyclerView(mRecyclerViewPaymentGateway);

        mWalletSnapHelper = new LinearSnapHelper();
        mWalletSnapHelper.attachToRecyclerView(mRecyclerViewWallets);

        mPaymentGatewaySnapHelper = new LinearSnapHelper();
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

                    View centerView = mWalletSnapHelper.findSnapView(recyclerView.getLayoutManager());
                    mSelectedWalletPosition = recyclerView.getLayoutManager().getPosition(centerView) - 1;
                    ((ChargeWalletRecyclerViewAdapter) mRecyclerViewWallets.getAdapter()).select(mSelectedWalletPosition + 1);
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

        mButtonChargeNext.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ChargeAmountActivity.class);
            intent.putExtra("walletId", mWalletList.get(mSelectedWalletPosition).getId());
            intent.putExtra("paymentGatewayName", "braintree");
            startActivity(intent);
        });
    }

    public void getWalletList() {

        mPresenter.getWalletList();
    }

    @Override
    public void setListWallet(List<Wallet> walletList) {

        mWalletList = walletList;
        mRecyclerViewAdapterChargeWallet.setDataSource(walletList);

        //TODO:(Ehsan) get it as an array from resource
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.charge_braintreelogo);
        mRecyclerViewAdapterChargePaymentGateway.setDataSource(list);
    }

    @Override
    public void setPresenter(ChargeContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

        MaterialDialogUtil.getInstance().showMaterialDialog(getContext());

    }

    @Override
    public void dismissProgressBar() {

        MaterialDialogUtil.getInstance().dismissMaterialDialog();

    }
}
