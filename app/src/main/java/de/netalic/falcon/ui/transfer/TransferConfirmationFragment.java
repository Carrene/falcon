package de.netalic.falcon.ui.transfer;

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
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferConfirmationFragment extends Fragment implements TransferConfirmationContract.View {

    private View mRoot;
    private Button mButtonConfirm;
    private TextView mTextViewWalletName;
    private TextView mTextViewDestinationWalletAddress;
    private TextView mTextViewTransferAmount;
    private TransferConfirmationContract.Presenter mTransferConfirmationPresenter;
    private Transaction mTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferconfirmation, null);
        checkNotNull(getArguments());
        mTransaction = getArguments().getParcelable(TransferPayeeFragment.ARGUMENT_TRANSACTION);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        initListener();
        setTransferInformation();
    }

    private void initUiComponent() {

        mTextViewWalletName = mRoot.findViewById(R.id.textview_transferconfirmation_walletname);
        mTextViewDestinationWalletAddress = mRoot.findViewById(R.id.textview_transferconfirmation_payee);
        mTextViewTransferAmount = mRoot.findViewById(R.id.textview_transferconfirmation_transferamount);
    }

    @Override
    public void setPresenter(TransferConfirmationContract.Presenter presenter) {

        mTransferConfirmationPresenter = presenter;
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

    public static TransferConfirmationFragment newInstance(Transaction transaction) {

        TransferConfirmationFragment transferConfirmationFragment = new TransferConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TransferPayeeFragment.ARGUMENT_TRANSACTION, transaction);
        transferConfirmationFragment.setArguments(bundle);

        return transferConfirmationFragment;
    }


    @Override
    public void navigationToCompletedTransfer(Transaction transaction) {

        Intent intent = new Intent(getContext(), TransferCompletedActivity.class);
        intent.putExtra(TransferPayeeFragment.ARGUMENT_TRANSACTION, transaction);
        startActivity(intent);
    }

    @Override
    public void showErrorTransferNotFound404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_transfernotfound), getContext());

    }

    @Override
    public void showErrorTryingToFinalizeSomeoneElseTransaction404() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_tryingtofinalizesomeoneelsetransaction), getContext());
    }

    @Override
    public void shoeErrorFinalizingTransactionWithStatusOfSucceed604() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_tryingtofinalizesomeoneelsetransaction), getContext());
    }

    @Override
    public void shoeErrorFinalizingTransactionWithStatusOfFailed604() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_finalizingtransactionwithstatusoffailed), getContext());
    }

    @Override
    public void showError600() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_balanceislowerthanbalance), getContext());
    }

    @Override
    public void showError401() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_finalizetransferasananonymous), getContext());

    }


    private void initListener() {

        mButtonConfirm.setOnClickListener(v -> {

            mTransferConfirmationPresenter.finalizeTransfer(mTransaction.getId());

        });

    }

    private void setTransferInformation() {

        mTextViewWalletName.setText(mTransaction.getActionList().get(1).getWalletName());
        mTextViewTransferAmount.setText(mTransaction.getActionList().get(1).getCurrencySymbol() + mTransaction.getActionList().get(1).getAmount());
        mTextViewDestinationWalletAddress.setText(mTransaction.getActionList().get(1).getWalletAddress());
    }
}
