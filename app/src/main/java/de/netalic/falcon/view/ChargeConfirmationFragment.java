package de.netalic.falcon.view;

import android.app.Activity;
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

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeConfirmationContract;
import de.netalic.falcon.util.MaterialDialogUtil;

public class ChargeConfirmationFragment extends Fragment implements ChargeConfirmationContract.View {

    private ChargeConfirmationContract.Presenter mChargeConfirmationPresenter;
    private View mRoot;
    private Deposit mDeposit;
    private TextView mTextViewWalletName;
    private TextView mTextViewWalletNo;
    private TextView mTextViewChargeAmount;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewPaymentGateway;
    private TextView mTextViewTransactionAmount;
    private Button mButtonConfirm;
    public static final String ARGUMENT_CHARGE_START = "chargeStart";
    private static final int DROP_IN_REQUEST = 1;
    private static final String ARGUMENT_DEPOSIT = "DEPOSIT";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_chargeconfirmation, null);
        if (getArguments() == null) {
            throw new RuntimeException("Charge response should not be null!");
        }
        mDeposit = getArguments().getParcelable(ARGUMENT_CHARGE_START);

        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        initUiComponent();
        setPaymentConfirmationData();
        initListener();

    }

    private void setPaymentConfirmationData() {

        mTextViewWalletName.setText(mDeposit.getWalletName());
        mTextViewWalletNo.setText(String.valueOf(mDeposit.getWalletId()));
        mTextViewChargeAmount.setText(String.valueOf(mDeposit.getChargeAmount()));
        mTextViewPaidAmount.setText(String.valueOf(mDeposit.getPaidAmount()));
        mTextViewPaymentGateway.setText(mDeposit.getPaymentGatewayName());

    }

    private void initListener() {

        mButtonConfirm.setOnClickListener(v -> {

            String braintreeToken = mDeposit.getBraintreeToken();
            DropInRequest dropInRequest = new DropInRequest()
                    .clientToken(braintreeToken);
            startActivityForResult(dropInRequest.getIntent(getContext()), DROP_IN_REQUEST);
        });
    }

    @Override
    public void setPresenter(ChargeConfirmationContract.Presenter presenter) {

        mChargeConfirmationPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        MaterialDialogUtil.getInstance().showMaterialDialog(getContext());
    }

    @Override
    public void dismissProgressBar() {

        MaterialDialogUtil.getInstance().dismissMaterialDialog();
    }

    public static ChargeConfirmationFragment newInstance(Deposit deposit) {

        ChargeConfirmationFragment fragment = new ChargeConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_CHARGE_START, deposit);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_chargeconfirmation_walletname);
        mTextViewWalletNo = mRoot.findViewById(R.id.textview_chargeconfirmation_walletno);
        mTextViewChargeAmount = mRoot.findViewById(R.id.textview_chargeconfirmation_chargeamount);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_chargeconfirmation_paidamount);
        mTextViewPaymentGateway = mRoot.findViewById(R.id.textview_chargeconfirmation_paymentgateway);
        mButtonConfirm = mRoot.findViewById(R.id.button_chargeconfirmation_confirm);
        mTextViewTransactionAmount = mRoot.findViewById(R.id.textview_chargeconfirmation_transactionamount);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                mRoot.setVisibility(View.GONE);
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String braintreeNonce = result.getPaymentMethodNonce().getNonce();

                mChargeConfirmationPresenter.finalize(mDeposit.getWalletId(), mDeposit.getId(), braintreeNonce);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //TODO(Milad): Payment is cancelled
            } else {
                // an error occurred, checked the returned exception
                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    @Override
    public void navigationToChargeCompleted(Deposit deposit) {

        Intent intent = new Intent(getActivity(), ChargeCompletedActivity.class);
        intent.putExtra(ARGUMENT_DEPOSIT, deposit);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void navigationToChargeFailed(Deposit deposit) {

        Intent intent = new Intent(getActivity(), ChargeFailedActivity.class);
        intent.putExtra(ARGUMENT_DEPOSIT, deposit);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
