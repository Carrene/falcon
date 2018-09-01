package de.netalic.falcon.ui.transfer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferConfirmationFragment extends Fragment implements TransferConfirmationContract.View {

    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferconfirmation, null);
        return mRoot;
    }

    @Override
    public void setPresenter(TransferConfirmationContract.Presenter presenter) {

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

    public static TransferConfirmationFragment newInstance() {

        return new TransferConfirmationFragment();
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
}
