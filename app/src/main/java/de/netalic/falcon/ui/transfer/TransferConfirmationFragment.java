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

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferConfirmationFragment extends Fragment implements TransferConfirmationContract.View {

    private View mRoot;
    private int mSourceWalletAddress;
    private int mDestinationWalletAddress;
    private double mTransferAmount;
    private Button mButtonConfirm;
    private TransferConfirmationContract.Presenter mTransferConfirmationPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferconfirmation, null);
        checkNotNull(getArguments());
        mSourceWalletAddress = getArguments().getInt(TransferAmountFragment.ARGUMENT_WALLET_ADDRESS);
        mDestinationWalletAddress = getArguments().getInt(TransferPayeeFragment.ARGUMENT_DESTINATION_WALLET_ADDRESS);
        mTransferAmount = getArguments().getDouble(TransferPayeeFragment.ARGUMENT_TRANSFER_AMOUNT);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        initListener();
    }

    private void initUiComponent() {

        mButtonConfirm = mRoot.findViewById(R.id.button_transferconfirmation_confirm);
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

    public static TransferConfirmationFragment newInstance(int sourceWalletAddress, int destinationWalletAddress, double transferAmount) {

        TransferConfirmationFragment transferConfirmationFragment = new TransferConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TransferAmountFragment.ARGUMENT_WALLET_ADDRESS, sourceWalletAddress);
        bundle.putInt(TransferPayeeFragment.ARGUMENT_DESTINATION_WALLET_ADDRESS, destinationWalletAddress);
        bundle.putDouble(TransferPayeeFragment.ARGUMENT_TRANSFER_AMOUNT, transferAmount);
        transferConfirmationFragment.setArguments(bundle);

        return transferConfirmationFragment;
    }

    @Override
    public void navigationToCompletedTransfer() {

        Intent intent = new Intent(getContext(), TransferCompletedActivity.class);
        startActivity(intent);

    }

    @Override
    public void navigationToTransferFailed() {

        Intent intent = new Intent(getContext(), TransferFailedActivity.class);
        startActivity(intent);
    }

    @Override
    public void showResponseCodeInvalidSourceWalletAddress() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_invalidsourcewalletaddress), getContext());

    }

    @Override
    public void showResponseCodeInvalidDestinationWalletAddress() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_invaliddestinationwalletaddress), getContext());

    }

    @Override
    public void showResponseCodeSourceWalletNotFound() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_sourcewalletnotfound), getContext());
    }

    @Override
    public void showResponseCodeSourceAndDestinationIsSame() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_sourceanddestinationissame), getContext());

    }

    @Override
    public void showResponseCodeInvalidAmount() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_invalidamount), getContext());

    }

    @Override
    public void showResponseCodeAmountIsNegative() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_amountisnegative), getContext());

    }

    @Override
    public void showResponseInsufficientBalance() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_insufficientbalance), getContext());

    }

    @Override
    public void showResponseTryingToTransferFromAnotherClientWallet() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferconfirmation_tryingtotransferfromanotherclientwallet), getContext());
    }

    private void initListener() {

        mButtonConfirm.setOnClickListener(v ->

                mTransferConfirmationPresenter.transfer(mSourceWalletAddress, mTransferAmount, mDestinationWalletAddress));
    }
}
