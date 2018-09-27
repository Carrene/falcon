package de.netalic.falcon.ui.transfer;

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

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferFragment extends Fragment implements TransferContract.View {

    private View mRoot;
    private TransferContract.Presenter mTransferPresenter;
    private TransferSpinnerAdapter mTransferSpinnerAdapter;
    private Button mButtonNextAmount;
    private TextView mTextViewBalance;
    private Spinner mSpinnerWalletList;
    private List<Wallet> mWalletList;
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transfer, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        getListWallet();
        initListener();

    }

    @Override
    public void setPresenter(TransferContract.Presenter presenter) {

        mTransferPresenter = checkNotNull(presenter);
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

    public static TransferFragment newInstance() {

        return new TransferFragment();
    }

    private void getListWallet() {

        mTransferPresenter.getWalletList();

    }

    private void initUiComponent() {

        mButtonNextAmount = mRoot.findViewById(R.id.button_transfer_nextamount);
        mTextViewBalance = mRoot.findViewById(R.id.textview_transfer_balance);
        mSpinnerWalletList = mRoot.findViewById(R.id.spinner_transfer_walletlist);


    }

    @Override
    public void setWalletList(List<Wallet> walletList) {

        mWalletList = walletList;
        mTransferSpinnerAdapter = new TransferSpinnerAdapter(getContext(), mWalletList);
        mSpinnerWalletList.setAdapter(mTransferSpinnerAdapter);

    }

    private void initListener() {

        mSpinnerWalletList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(position).getBalance()) + " " + mWalletList.get(position).getCurrencySymbol());
                mPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(0).getBalance()) + " " + mWalletList.get(0).getCurrencySymbol());
                mPosition = 0;

            }
        });

        mButtonNextAmount.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), TransferAmountActivity.class);
            intent.putExtra("walletAddress", mWalletList.get(mPosition).getId());
            startActivity(intent);

        });
    }
}
