package de.netalic.falcon.ui.transfer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import de.netalic.falcon.R;

public class TransferAmountFragment extends Fragment implements TransferAmountContract.View {

    private TransferAmountContract.Presenter mTransferAmountPresenter;
    private View mRoot;
    private EditText mEditTextWalletAmount;
    private EditText mEditTextBaseCurency;
    private TextView mTextViewUseMaximum;
    private TextView mTextViewUseMinimum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_transferamount,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUiComponent();
    }

    private void initUiComponent(){

        mEditTextWalletAmount=mRoot.findViewById(R.id.edittext_transferamount_amountwallet);
        mEditTextBaseCurency=mRoot.findViewById(R.id.edittext_transferamount_amountbase);
        mTextViewUseMinimum=mRoot.findViewById(R.id.textview_transferamount_useminimum);
        mTextViewUseMaximum=mRoot.findViewById(R.id.textview_transferamount_usemaximum);

    }

    @Override
    public void setPresenter(TransferAmountContract.Presenter presenter) {

        mTransferAmountPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransferAmountFragment newInstance() {

        return new TransferAmountFragment();
    }
}
