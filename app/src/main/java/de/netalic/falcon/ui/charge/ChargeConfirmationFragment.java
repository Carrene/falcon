package de.netalic.falcon.ui.charge;

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

import java.text.DecimalFormat;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeConfirmationFragment extends Fragment implements ChargeConfirmationContract.View {

    private ChargeConfirmationContract.Presenter mChargeConfirmationPresenter;
    private View mRoot;
    private Transaction mTransaction;
    private TextView mTextViewWalletName;
    private TextView mTextViewChargeAmount;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewPaymentGateway;
    private Button mButtonConfirm;
    public static final String ARGUMENT_CHARGE_START = "chargeStart";
    private static final int DROP_IN_REQUEST = 1;
    private static final String ARGUMENT_TRANSACTION = "TRANSACTION";
    private DecimalFormat mDecimalFormat;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_chargeconfirmation, null);
        if (getArguments() == null) {
            throw new RuntimeException("Charge response should not be null!");
        }

        mTransaction = getArguments().getParcelable(ARGUMENT_CHARGE_START);
        mDecimalFormat = new DecimalFormat("0.00##");
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

        mTextViewWalletName.setText(mTransaction.getActionList().get(1).getWalletName());
        mTextViewChargeAmount.setText(mTransaction.getActionList().get(1).getCurrencySymbol()+" "+String.valueOf(mTransaction.getActionList().get(1).getAmount()));
        mTextViewPaidAmount.setText(mTransaction.getActionList().get(1).getCurrencySymbol()+" "+String.valueOf(mDecimalFormat.format(Math.abs(mTransaction.getActionList().get(0).getAmount()))));
        mTextViewPaymentGateway.setText(mTransaction.getPaymentGatewayName());

    }

    private void initListener() {

        mButtonConfirm.setOnClickListener(v -> {

            String braintreeToken = mTransaction.getBraintreeToken();
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

    public static ChargeConfirmationFragment newInstance(Transaction transaction) {

        ChargeConfirmationFragment fragment = new ChargeConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_CHARGE_START, transaction);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_chargeconfirmation_walletname);
        mTextViewChargeAmount = mRoot.findViewById(R.id.textview_chargeconfirmation_chargeamount);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_chargeconfirmation_paidamount);
        mTextViewPaymentGateway = mRoot.findViewById(R.id.textview_chargeconfirmation_paymentgateway);
        mButtonConfirm = mRoot.findViewById(R.id.button_chargeconfirmation_confirm);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                mRoot.setVisibility(View.GONE);
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String braintreeNonce = result.getPaymentMethodNonce().getNonce();

                //mChargeConfirmationPresenter.finalizeCharge(mTransaction.getActionList().get(1).getWalletId(), mDeposit.getId(), braintreeNonce);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //TODO(Milad): Payment is cancelled
            } else {

                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    @Override
    public void navigationToChargeCompleted(Transaction transaction) {

        Intent intent = new Intent(getActivity(), ChargeCompletedActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION, transaction);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void navigationToChargeFailed(Transaction transaction) {

        Intent intent = new Intent(getActivity(), ChargeFailedActivity.class);
        intent.putExtra(ARGUMENT_TRANSACTION, transaction);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showErrorInvalidWalletId() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeconfirmation_invalidwalletid), getContext());
    }

    @Override
    public void showErrorWalletNotFound() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeconfirmation_walletnotfound), getContext());

    }

    @Override
    public void showErrorDepositNotFound() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeconfirmation_depositnotfound), getContext());
    }

    @Override
    public void showErrorInvalidDepositId() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeconfirmation_invaliddepositid), getContext());
    }

    @Override
    public void showErrorDepositAlreadySucceed() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeconfirmation_depositalreadyexist), getContext());
    }

    @Override
    public void showErrorInvalidBraintreeNonce() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.chargeconfirmation_invalidbraintreenoce), getContext());
    }
}
