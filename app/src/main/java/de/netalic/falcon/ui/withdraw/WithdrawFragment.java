package de.netalic.falcon.ui.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class WithdrawFragment extends Fragment implements WithdrawContract.View {


    private WithdrawContract.Presenter mPresenter;
    private View mRoot;
    private WithdrawSpinnerAdapter mWithdrawSpinnerAdapter;
    private Spinner mSpinnerWalletList;
    private List<Wallet> mWalletList;
    private TextView mTextViewBalance;
    private Button mButtonNextAmount;
    private static final String ARGUMENT_WALLET = "WALLET";
    private int mPosition;

    @Override
    public void setPresenter(WithdrawContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRoot = inflater.inflate(R.layout.fragment_withdraw, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        getWalletList();
        initListener();
    }

    public static WithdrawFragment newInstance() {

        return new WithdrawFragment();

    }

    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
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

    private void initUiComponent() {

        mSpinnerWalletList = mRoot.findViewById(R.id.spinner_withdraw_spinner);
        mTextViewBalance = mRoot.findViewById(R.id.textview_withdraw_balance);
        mButtonNextAmount = mRoot.findViewById(R.id.button_withdraw_nextamount);
    }


    private void getWalletList() {

        mPresenter.getWalletList();
    }

    @Override
    public void setListWallet(List<Wallet> walletList) {

        mWalletList = walletList;
        mWithdrawSpinnerAdapter = new WithdrawSpinnerAdapter(getContext(), mWalletList);
        mSpinnerWalletList.setAdapter(mWithdrawSpinnerAdapter);
    }

    @Override
    public void internetConnectionError() {

        SnackbarUtil.showSnackbar(mRoot,getString(R.string.everywhere_connectionerror),getContext());
    }

    private void initListener() {

        mSpinnerWalletList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mTextViewBalance.setText(String.valueOf(Double.valueOf(mWalletList.get(position).getBalance()).longValue())+" "+mWalletList.get(position).getCurrencySymbol());
                mPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(0).getBalance())+" "+mWalletList.get(0).getCurrencySymbol());
            }
        });

        mButtonNextAmount.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), WithdrawAmountActivity.class);
            intent.putExtra(ARGUMENT_WALLET, mWalletList.get(mPosition));
            startActivity(intent);

        });
    }
}
