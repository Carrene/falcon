package de.netalic.falcon.ui.transfer;

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
import de.netalic.falcon.model.Wallet;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferFragment extends Fragment implements TransferContract.View {

    private View mRoot;
    private TransferContract.Presenter mTransferPresenter;
    private TransferSpinnerAdapter mTransferSpinnerAdapter;
    private Button mbuttonNextAmount;
    private TextView mTextViewBalance;
    private Spinner mSpinnerWalletList;
    private List<Wallet> mWalletList;

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

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransferFragment newInstance() {

        return new TransferFragment();
    }

    private void getListWallet(){

        mTransferPresenter.getWalletList();

    }

    private void initUiComponent() {

        mbuttonNextAmount = mRoot.findViewById(R.id.button_transfer_nextamount);
        mTextViewBalance = mRoot.findViewById(R.id.textview_transfer_balance);
        mSpinnerWalletList = mRoot.findViewById(R.id.spinner_transfer_walletlist);


    }

    @Override
    public void setWalletList(List<Wallet> walletList) {

        mWalletList = walletList;
        mTransferSpinnerAdapter = new TransferSpinnerAdapter(getContext(), mWalletList);
        mSpinnerWalletList.setAdapter(mTransferSpinnerAdapter);

    }

    private void initListener(){

        mSpinnerWalletList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(position).getBalance()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(0).getBalance()));

            }
        });
    }
}
