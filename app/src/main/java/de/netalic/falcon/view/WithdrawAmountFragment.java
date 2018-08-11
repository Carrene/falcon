package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.WithdrawAmountContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawAmountFragment extends Fragment implements WithdrawAmountContract.View {

    private View mRoot;
    private WithdrawAmountContract.Presenter mPresenter;
    private EditText mEditTextWalletAmount;
    private EditText mEditTextBaseCurrency;
    private EditText mEditTextOtherCurrency;
    private TextView mTextViewUsemaximm;
    private TextView mTextViewUseMinimum;
    private Button mbuttonNextWithdraw;
    private static final String ARGUMENT_WALLET="WALLET";
    private Wallet mWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_withdrawamount,null);
        mWallet = getArguments().getParcelable(ARGUMENT_WALLET);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponent();


    }

    @Override
    public void setPresenter(WithdrawAmountContract.Presenter presenter) {

        mPresenter=checkNotNull(presenter);

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static WithdrawAmountFragment newInstance(Wallet wallet) {


        WithdrawAmountFragment fragment = new WithdrawAmountFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_WALLET, wallet);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent(){

        mEditTextWalletAmount=mRoot.findViewById(R.id.edittext_withdrawamount_walletamount);
        mEditTextBaseCurrency=mRoot.findViewById(R.id.edittext_withdrawamount_basecurrency);
        mEditTextOtherCurrency=mRoot.findViewById(R.id.edittext_withdrawamount_othercurrency);
        mTextViewUsemaximm=mRoot.findViewById(R.id.textview_withdrawamount_usemaximum);
        mTextViewUseMinimum=mRoot.findViewById(R.id.textview_withdraw_useminimum);
        mbuttonNextWithdraw=mRoot.findViewById(R.id.button_withdrawamount_nextwithdraw);
    }

}
