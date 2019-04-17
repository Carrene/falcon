package de.netalic.falcon.ui.load;

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
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoadConfirmationFragment extends Fragment implements LoadConfirmationContract.View {

    private LoadConfirmationContract.Presenter mChargeConfirmationPresenter;
    private View mRoot;
    private Transaction mTransaction;
    private TextView mTextViewLoadAmount;
    private TextView mTextViewPaidAmount;
    private TextView mTextViewWalletType;
    private TextView mTextViewCurrencySymbol;
    private TextView mTextViewBalance;
    private Button mButtonConfirm;
    public static final String ARGUMENT_CHARGE_START = "chargeStart";
    private static final int DROP_IN_REQUEST = 1;
    public static final String ARGUMENT_RECEIPT = "receipt";
    private DecimalFormat mDecimalFormat;
    private Wallet mSelectedWallet;
    public static final String SELECTED_WALLET = "wallet";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_loadconfirmation, null);
        mSelectedWallet = getArguments().getParcelable(DashboardFragment.SELECTED_WALLET);
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
        getUser();

        mTextViewWalletType.setText(mSelectedWallet.getCurrencyCode());
        mTextViewBalance.setText(String.valueOf(mSelectedWallet.getBalance()));
        mTextViewCurrencySymbol.setText(mSelectedWallet.getCurrencySymbol());

    }

    private void getUser() {


    }

    private void setPaymentConfirmationData() {

        mTextViewLoadAmount.setText(mTransaction.getActionList().get(1).getCurrencySymbol() + " " + String.valueOf(Math.abs(mTransaction.getActionList().get(1).getAmount())));
        mTextViewPaidAmount.setText(mTransaction.getActionList().get(0).getCurrencySymbol() + " " + String.valueOf(mDecimalFormat.format(Math.abs(mTransaction.getActionList().get(0).getAmount()))));

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
    public void setPresenter(LoadConfirmationContract.Presenter presenter) {

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

    public static LoadConfirmationFragment newInstance(Transaction transaction,Wallet wallet) {

        LoadConfirmationFragment fragment = new LoadConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_CHARGE_START, transaction);
        bundle.putParcelable(SELECTED_WALLET,wallet);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mTextViewLoadAmount = mRoot.findViewById(R.id.textview_chargeconfirmation_loadamount);
        mTextViewPaidAmount = mRoot.findViewById(R.id.textview_chargeconfirmation_paidamount);
        mButtonConfirm = mRoot.findViewById(R.id.button_chargeconfirmation_confirm);
        mTextViewWalletType = mRoot.findViewById(R.id.textview_everywhereribbonheader_wallettype);
        mTextViewCurrencySymbol = mRoot.findViewById(R.id.textview_everywhereribbonheader_currencysymbol);
        mTextViewBalance = mRoot.findViewById(R.id.textview_everywhereribbonheader_walletbalance);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                mRoot.setVisibility(View.GONE);
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String braintreeNonce = result.getPaymentMethodNonce().getNonce();

                mChargeConfirmationPresenter.finalizeCharge(mTransaction.getId(), braintreeNonce);
            } else if (resultCode == Activity.RESULT_CANCELED) {

                SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadconfirmation_paymentiscanceled), getContext());
            } else {

                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    @Override
    public void navigationToChargeCompleted(Receipt receipt) {

        Intent intent = new Intent(getActivity(), LoadCompletedActivity.class);
        intent.putExtra(ARGUMENT_RECEIPT, receipt);
        intent.putExtra("load",mTransaction.getActionList().get(0).getCurrencySymbol());
        intent.putExtra("paid",mTransaction.getActionList().get(1).getCurrencySymbol());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void navigationToChargeFailed(Receipt receipt) {

        Intent intent = new Intent(getActivity(), LoadFailedActivity.class);
        intent.putExtra(ARGUMENT_RECEIPT, receipt);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Override
    public void showErrorBraintreeNonceIsMissing() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadconfirmation_braintreenonceismissing), getContext());

    }


    @Override
    public void showErrorCannotFinalizeFailedTransaction() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadconfirmation_cannotfinalizefailedtransaction), getContext());
    }

    @Override
    public void showErrorTransactionNotFound() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadconfirmation_transactionnotfound), getContext());

    }

    @Override
    public void showErrorInvalidTransactionId() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadconfirmation_invalidtransactionid), getContext());
    }

    @Override
    public void showErrorFinalizeTransferAsAnAnonymous() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadconfirmation_finalizetransferasananonymous), getContext());
    }

    @Override
    public void showErrorWhenFailed() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.loadconfirmation_pleasetryagain), getContext());
    }
}
