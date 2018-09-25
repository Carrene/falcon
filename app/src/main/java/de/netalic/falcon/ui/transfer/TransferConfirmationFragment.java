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
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferConfirmationFragment extends Fragment implements TransferConfirmationContract.View {

    private View mRoot;
    private String mWalletName;
    private String mDestinationWalletAddress;
    private float mTransferAmount;
    private Button mButtonConfirm;
    private TextView mTextViewWalletName;
    private TextView mTextViewDestinationWalletAddress;
    private TextView mTextViewTransferAmount;
    private String mCurrencySymbol;
    private TransferConfirmationContract.Presenter mTransferConfirmationPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferconfirmation, null);
        checkNotNull(getArguments());
        mWalletName = getArguments().getString(TransferPayeeFragment.ARGUMENT_WALLET_NAME);
        mDestinationWalletAddress = getArguments().getString(TransferPayeeFragment.ARGUMENT_DESTINATION_WALLET_ADDRESS);
        mTransferAmount = getArguments().getFloat(TransferPayeeFragment.ARGUMENT_TRANSFER_AMOUNT);
        mCurrencySymbol=getArguments().getString(TransferPayeeFragment.ARGUMENT_CURRENCY_SYMBOL);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        initListener();
        setTransferInformation();
    }

    private void initUiComponent() {

        mButtonConfirm = mRoot.findViewById(R.id.button_transferconfirmation_confirm);
        mTextViewWalletName=mRoot.findViewById(R.id.textview_transferconfirmation_walletname);
        mTextViewDestinationWalletAddress=mRoot.findViewById(R.id.textview_transferconfirmation_payee);
        mTextViewTransferAmount=mRoot.findViewById(R.id.textview_transferconfirmation_transferamount);
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

    public static TransferConfirmationFragment newInstance(String walletName, String destinationWalletAddress, float transferAmount,String currencySymbol) {

        TransferConfirmationFragment transferConfirmationFragment = new TransferConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TransferPayeeFragment.ARGUMENT_WALLET_NAME, walletName);
        bundle.putString(TransferPayeeFragment.ARGUMENT_DESTINATION_WALLET_ADDRESS, destinationWalletAddress);
        bundle.putFloat(TransferPayeeFragment.ARGUMENT_TRANSFER_AMOUNT, transferAmount);
        bundle.putString(TransferPayeeFragment.ARGUMENT_CURRENCY_SYMBOL,currencySymbol);
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

 }

 private void setTransferInformation(){

        mTextViewWalletName.setText(mWalletName);
        mTextViewTransferAmount.setText(mCurrencySymbol+String.valueOf(mTransferAmount));
        mTextViewDestinationWalletAddress.setText(mDestinationWalletAddress);
 }
}
