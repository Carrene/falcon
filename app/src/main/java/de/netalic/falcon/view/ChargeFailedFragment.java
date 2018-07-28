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
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeFailedContract;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFailedFragment extends Fragment implements ChargeFailedContract.View {

    private ChargeFailedContract.Presenter mChargeFailedPresenter;
    private View mRoot;
    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";
    private Deposit mDeposit;
    private TextView mTextViewWalletName;
    private TextView mTextViewChargeAmount;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewPaymentGateway;
    private TextView mTextViewTransactionDate;
    private Button mButtonNavigationDashboard;

    @Override
    public void setPresenter(ChargeFailedContract.Presenter presenter) {

        mChargeFailedPresenter=checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRoot=inflater.inflate(R.layout.fragment_chargefailed,null);
        mDeposit=getArguments().getParcelable(ARGUMENT_DEPOSIT);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        setPaymentInformation();
        initListener();
    }

    public static ChargeFailedFragment newInstance(Deposit deposit) {

        Bundle bundle=new Bundle();
        bundle.putParcelable(ARGUMENT_DEPOSIT,deposit);
        ChargeFailedFragment fragment = new ChargeFailedFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent(){

        mTextViewWalletName =mRoot.findViewById(R.id.textview_chargefailed_walletname);
        mTextViewChargeAmount=mRoot.findViewById(R.id.textview_chargefailed_amountalpha);
        mTextViewPaidAmount=mRoot.findViewById(R.id.textview_chargefailed_amountdollar);
        mTextViewPaymentGateway=mRoot.findViewById(R.id.textview_chargefailed_paymentgateway);
        mTextViewTransactionDate=mRoot.findViewById(R.id.textview_chargefailed_transactiondate);
        mButtonNavigationDashboard=mRoot.findViewById(R.id.button_chargefailed_dashborad);

    }

    public void setPaymentInformation(){

        mTextViewWalletName.setText(mDeposit.getWalletName());
        mTextViewChargeAmount.setText(String.valueOf(mDeposit.getChargeAmount()));
        mTextViewPaidAmount.setText(String.valueOf(mDeposit.getPaidAmount()));
        mTextViewPaymentGateway.setText(mDeposit.getPaymentGatewayName());
        mTextViewTransactionDate.setText(mDeposit.getModifiedAt());
    }

    public void initListener(){

        mButtonNavigationDashboard.setOnClickListener(v -> {

            Intent intent=new Intent(getActivity(),DashboardActivity.class);
            startActivity(intent);
        });

    }
}
