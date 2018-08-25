package de.netalic.falcon.ui.transfer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import de.netalic.falcon.R;
import static com.google.common.base.Preconditions.checkNotNull;

public class TransferFragment extends Fragment implements TransferContract.View {

    private View mRoot;
    private TransferContract.Presenter mTransferPresenter;
    private TransferSpinnerAdapter mTransferSpinnerAdapter;
    private Button mbuttonNextAmount;
    private TextView mTextViewBalance;
    private Spinner mSpinnerWalletList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transfer, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();

    }

    @Override
    public void setPresenter(TransferContract.Presenter presenter) {

        mTransferPresenter = checkNotNull(presenter);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransferFragment newInstance() {

        return new TransferFragment();
    }

    private void initUiComponent(){

        mbuttonNextAmount=mRoot.findViewById(R.id.button_transfer_nextamount);
        mTextViewBalance=mRoot.findViewById(R.id.textview_transfer_balance);
        mSpinnerWalletList=mRoot.findViewById(R.id.spinner_transfer_walletlist);


    }
}
