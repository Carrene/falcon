package de.netalic.falcon.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardFragment;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class SendConfirmationFragment extends Fragment implements SendConfirmationContract.View {

    private View mRoot;
    private TextView mTextViewPayeeAddress;
    private TextView mTextViewPayeeAmount;
    private TextView mTextViewReceivedAmount;
    private TextView mTextViewTransactionFee;
    private TextView mTextViewTotal;
    private TextView mTextViewPayeeAmountCurrencySymbol;
    private TextView mTextViewReceivedAmountCurrencySymbol;
    private TextView mTextViewTransactionFeeCurrencySymbol;
    private TextView mTextViewTotalCurrencySymbol;
    private SendConfirmationContract.Presenter mSendConfirmationPresenter;
    private Transaction mTransaction;
    private TextView mTextViewWalletType;
    private TextView mTextViewCurrencySymbol;
    private TextView mTextViewBalance;
    private Wallet mSelectedWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_sendconfirmation, null);
        checkNotNull(getArguments());
        mTransaction = getArguments().getParcelable(SendFragment.ARGUMENT_TRANSACTION);
        mSelectedWallet = getArguments().getParcelable(DashboardFragment.SELECTED_WALLET);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        setTransferInformation();
        setHasOptionsMenu(true);
        mTextViewWalletType.setText(mSelectedWallet.getCurrencyCode());
        mTextViewBalance.setText(String.valueOf(mSelectedWallet.getBalance()));
        mTextViewCurrencySymbol.setText(mSelectedWallet.getCurrencySymbol());
    }

    private void initUiComponent() {

        mTextViewPayeeAddress = mRoot.findViewById(R.id.textview_transferconfirmation_payeeaddress);
        mTextViewPayeeAmount = mRoot.findViewById(R.id.textview_transferconfirmation_payeeamount);
        mTextViewReceivedAmount = mRoot.findViewById(R.id.textview_transferconfirmation_receiveamount);
        mTextViewTransactionFee=mRoot.findViewById(R.id.textview_transferconfirmation_transactionfee);
        mTextViewTotal=mRoot.findViewById(R.id.textview_transferconfirmation_total);
        mTextViewPayeeAmountCurrencySymbol=mRoot.findViewById(R.id.textview_transferconfirmation_payeeamountcurrencysymbol);
        mTextViewReceivedAmountCurrencySymbol=mRoot.findViewById(R.id.textview_transferconfirmation_receiveamountcurrencysymbol);
        mTextViewTransactionFeeCurrencySymbol=mRoot.findViewById(R.id.textview_transferconfirmation_transactionfeecurrencysymbol);
        mTextViewTotalCurrencySymbol=mRoot.findViewById(R.id.textview_transferconfirmation_totalcurrencysymbol);
        mTextViewWalletType = mRoot.findViewById(R.id.textview_everywhereribbonheader_wallettype);
        mTextViewCurrencySymbol = mRoot.findViewById(R.id.textview_everywhereribbonheader_currencysymbol);
        mTextViewBalance = mRoot.findViewById(R.id.textview_everywhereribbonheader_walletbalance);
    }

    @Override
    public void setPresenter(SendConfirmationContract.Presenter presenter) {

        mSendConfirmationPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

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

    public static SendConfirmationFragment newInstance(Transaction transaction,Wallet selectedWallet) {

        SendConfirmationFragment transferConfirmationFragment = new SendConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SendFragment.ARGUMENT_TRANSACTION, transaction);
        bundle.putParcelable(DashboardFragment.SELECTED_WALLET,selectedWallet);
        transferConfirmationFragment.setArguments(bundle);
        return transferConfirmationFragment;
    }

    @Override
    public void navigationToCompletedTransfer(Transaction transaction) {

        Intent intent = new Intent(getContext(), SendCompletedActivity.class);
        intent.putExtra(SendFragment.ARGUMENT_TRANSACTION, transaction);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_everywhere_thathastick,menu);
    }

    @Override
    public void showErrorTransferNotFound404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.sendconfirmation_transfernotfound), getContext());

    }

    @Override
    public void showErrorTryingToFinalizeSomeoneElseTransaction404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.sendconfirmation_tryingtofinalizesomeoneelsetransaction), getContext());
    }

    @Override
    public void shoeErrorFinalizingTransactionWithStatusOfSucceed604() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.sendconfirmation_tryingtofinalizesomeoneelsetransaction), getContext());
    }

    @Override
    public void shoeErrorFinalizingTransactionWithStatusOfFailed604() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.sendconfirmation_finalizingtransactionwithstatusoffailed), getContext());
    }

    @Override
    public void showError600() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.sendconfirmation_balanceislowerthanbalance), getContext());
    }

    @Override
    public void showError401() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.sendconfirmation_finalizetransferasananonymous), getContext());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_everywhere_done:{

                mSendConfirmationPresenter.finalizeTransfer(mTransaction.getId());
            }
            break;
        }
        return true;
    }

    private void setTransferInformation() {

        mTextViewPayeeAddress.setText(mTransaction.getActionList().get(1).getWalletAddress());
        mTextViewReceivedAmount.setText(String.valueOf(Math.abs(mTransaction.getActionList().get(1).getAmount())));
        mTextViewPayeeAmount.setText(String.valueOf(Math.abs(mTransaction.getActionList().get(0).getAmount())));
        //mTextViewTransactionFee.setText(String.valueOf(Math.abs((Math.abs(mTransaction.getActionList().get(1).getAmount()))-
              //  Math.abs(mTransaction.getActionList().get(0).getAmount()))));
       // mTextViewTotal.setText(String.valueOf(Math.abs(mTransaction.getActionList().get(1).getAmount())));
        mTextViewPayeeAmountCurrencySymbol.setText(mTransaction.getActionList().get(0).getCurrencySymbol());
        mTextViewReceivedAmountCurrencySymbol.setText(mTransaction.getActionList().get(1).getCurrencySymbol());
        mTextViewTransactionFeeCurrencySymbol.setText(mTransaction.getActionList().get(0).getCurrencySymbol());
        mTextViewTotalCurrencySymbol.setText(mTransaction.getActionList().get(0).getCurrencySymbol());
    }
}