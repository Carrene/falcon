package de.netalic.falcon.ui.transfer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import de.netalic.falcon.R;
import de.netalic.falcon.util.SnackbarUtil;

public class TransferPayeeFragment extends Fragment implements TransferPayeeContract.View {


    private TransferPayeeContract.Presenter mTransferPayeePresenter;
    private Button mButtonNextTransfer;
    private EditText mEditTextWalletAddress;
    private View mRoot;
    public static final String ARGUMENT_TRANSFER_AMOUNT="transferAmount";
    private int mWalletAddress;
    private double mTransferAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferpayee, null);
        mWalletAddress=getArguments().getInt(TransferAmountFragment.ARGUMENT_WALLET_ADDRESS);
        mTransferAmount=getArguments().getDouble(ARGUMENT_TRANSFER_AMOUNT);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUiComponent();
        initListener();
    }

    @Override
    public void setPresenter(TransferPayeeContract.Presenter presenter) {

        mTransferPayeePresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransferPayeeFragment newInstance(int walletAddress,double transferAmount) {

        TransferPayeeFragment transferPayeeFragment=new TransferPayeeFragment();

        Bundle bundle=new Bundle();
        bundle.putDouble(ARGUMENT_TRANSFER_AMOUNT, transferAmount);
        bundle.putInt(TransferAmountFragment.ARGUMENT_WALLET_ADDRESS,walletAddress);
        transferPayeeFragment.setArguments(bundle);
        return transferPayeeFragment;
    }

    private void initListener(){

        mButtonNextTransfer.setOnClickListener(v -> {

            if (mEditTextWalletAddress.getText().toString().equals("")){

                SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.everywhere_pleasefillbox),getContext());
            }
            else {


            }

        });
    }

    private void initUiComponent(){

        mButtonNextTransfer =mRoot.findViewById(R.id.button_transferpayee_nexttransfer);
        mEditTextWalletAddress=mRoot.findViewById(R.id.edittext_transferpayee_walletaddress);
    }
}
