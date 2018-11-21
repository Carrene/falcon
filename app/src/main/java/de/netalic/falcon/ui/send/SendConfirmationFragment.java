package de.netalic.falcon.ui.send;

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
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class SendConfirmationFragment extends Fragment implements SendConfirmationContract.View {

    private View mRoot;
    private TextView mTextViewWalletName;
    private TextView mTextViewDestinationWalletAddress;
    private TextView mTextViewTransferAmount;
    private SendConfirmationContract.Presenter mSendConfirmationPresenter;
    private Transaction mTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_sendconfirmation, null);
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

        mTextViewWalletName = mRoot.findViewById(R.id.textview_transferconfirmation_walletname);
        mTextViewDestinationWalletAddress = mRoot.findViewById(R.id.textview_transferconfirmation_payee);
        mTextViewTransferAmount = mRoot.findViewById(R.id.textview_transferconfirmation_transferamount);
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

    public static SendConfirmationFragment newInstance(Transaction transaction) {

        SendConfirmationFragment transferConfirmationFragment = new SendConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(SendFragment.ARGUMENT_TRANSACTION, transaction);
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

        mTextViewWalletName.setText(mTransaction.getActionList().get(1).getWalletName());
        mTextViewTransferAmount.setText(mTransaction.getActionList().get(1).getCurrencySymbol() + Math.abs(mTransaction.getActionList().get(1).getAmount()));
        mTextViewDestinationWalletAddress.setText(mTransaction.getActionList().get(1).getWalletAddress());
    }
}
