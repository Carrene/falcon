package de.netalic.falcon.ui.purchase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;

public class PurchaseFragment extends Fragment implements PurchaseContract.View {


    private PurchaseContract.Presenter mPurchasePresenter;
    private View mRoot;
    private List<Wallet> mWalletList;
    private Spinner mWalletListSpinner;
    private PurchaseSpinnerAdapter mPurchaseSpinnerAdapter;
    private TextView mTextViewBalance;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_purchase,null);
        setHasOptionsMenu(true);
        getWalletList();
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initUiListener();
    }

    @Override
    public void setPresenter(PurchaseContract.Presenter presenter) {

        mPurchasePresenter=presenter;
    }

    @Override
    public void showProgressBar() {


        ((BaseActivity)getActivity()).showMaterialDialog();
    }

    @Override
    public void dismissProgressBar() {

        ((BaseActivity)getActivity()).dismissMaterialDialog();
    }

    public static PurchaseFragment newInstance() {

        PurchaseFragment fragment = new PurchaseFragment();
        return fragment;
    }

    @Override
    public void setListWallet(List<Wallet> walletList) {

        mWalletList=walletList;
        mPurchaseSpinnerAdapter=new PurchaseSpinnerAdapter(getContext(),walletList);
        mWalletListSpinner.setAdapter(mPurchaseSpinnerAdapter);
    }

    private void initUiComponent(){

        mWalletListSpinner=mRoot.findViewById(R.id.spinner_purchase_walletlist);
        mTextViewBalance=mRoot.findViewById(R.id.textview_purchase_balance);
    }

    private void getWalletList(){

        mPurchasePresenter.getWalletList();
    }

    private void initUiListener(){

        mWalletListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(position).getBalance()+" "+mWalletList.get(position).getCurrencySymbol()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mTextViewBalance.setText(String.valueOf(mWalletList.get(0).getBalance()+" "+mWalletList.get(0).getCurrencySymbol()));
            }
        });

    }
}
