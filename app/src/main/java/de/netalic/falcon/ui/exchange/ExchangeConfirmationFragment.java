package de.netalic.falcon.ui.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.send.SendCompletedActivity;
import de.netalic.falcon.ui.send.SendFragment;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ExchangeConfirmationFragment extends Fragment implements ExchangeConfirmationContract.View {

    private View mRoot;
    private TextView mTextViewWalletName;
    private TextView mTextViewDestinationWalletAddress;
    private TextView mTextViewExchangeAmount;
    private ExchangeConfirmationContract.Presenter mExchangeConfirmationPresenter;
    private Transaction mTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_exchangeconfirmation, null);
        checkNotNull(getArguments());
        mTransaction = getArguments().getParcelable(SendFragment.ARGUMENT_TRANSACTION);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        setTransferInformation();
        setHasOptionsMenu(true);
    }

    private void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_exchangeconfirmation_walletname);
        mTextViewDestinationWalletAddress = mRoot.findViewById(R.id.textview_exchangeconfirmation_payee);
        mTextViewExchangeAmount = mRoot.findViewById(R.id.textview_exchangeconfirmation_exchangeamount);
    }


    @Override
    public void setPresenter(ExchangeConfirmationContract.Presenter presenter) {

        mExchangeConfirmationPresenter = presenter;
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

    public static ExchangeConfirmationFragment newInstance(Transaction transaction) {

        ExchangeConfirmationFragment exchangeConfirmationFragment = new ExchangeConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SendFragment.ARGUMENT_TRANSACTION, transaction);
        exchangeConfirmationFragment.setArguments(bundle);

        return exchangeConfirmationFragment;
    }


    @Override
    public void navigationToCompletedTransfer(Transaction transaction) {

        Intent intent = new Intent(getContext(), SendCompletedActivity.class);
        intent.putExtra(SendFragment.ARGUMENT_TRANSACTION, transaction);
        startActivity(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_everywhere_thathastick, menu);
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

        switch (item.getItemId()) {

            case R.id.menu_everywhere_done: {

                mExchangeConfirmationPresenter.finalizeTransfer(mTransaction.getId());
            }
            break;
        }
        return true;
    }

    private void setTransferInformation() {

        mTextViewWalletName.setText(mTransaction.getActionList().get(1).getWalletName());
        mTextViewExchangeAmount.setText(mTransaction.getActionList().get(1).getCurrencySymbol() + Math.abs(mTransaction.getActionList().get(1).getAmount()));
        mTextViewDestinationWalletAddress.setText(mTransaction.getActionList().get(1).getWalletAddress());
    }
}
