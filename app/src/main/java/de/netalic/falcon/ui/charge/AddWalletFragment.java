package de.netalic.falcon.ui.charge;

import android.content.Intent;
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
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;

public class AddWalletFragment extends Fragment implements AddWalletContract.View,ListCurrencyRecyclerViewAdapter.Callback {

    private AddWalletContract.Presenter mPresenter;
    private View mRoot;
    private Button mButtonCurrencyList;
    private EditText mEditTextWalletName;
    private Button mButtonAddWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_addwallet,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        initUiListener();

    }

    @Override
    public void setPresenter(AddWalletContract.Presenter presenter) {

        mPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

        ((BaseActivity)getActivity()).showMaterialDialog();
    }

    @Override
    public void dismissProgressBar() {

        ((BaseActivity)getActivity()).dismissMaterialDialog();

        }

    public static AddWalletFragment newInstance() {

        Bundle bundle = new Bundle();

        AddWalletFragment fragment = new AddWalletFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent(){

        mButtonCurrencyList =mRoot.findViewById(R.id.butten_addwallet_navigationtocurrencieslist);
        mEditTextWalletName =mRoot.findViewById(R.id.edittext_addwallet_walletname);
        mButtonAddWallet =mRoot.findViewById(R.id.button_addwallet_addwalletrequest);
    }

    private void initUiListener(){

        mButtonAddWallet.setOnClickListener(v -> {

            if (mEditTextWalletName.getText().toString().equals("") || mButtonCurrencyList.getText().toString().equals("")){

                SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.everywhere_pleasefillbox),getContext());
            }

            else {




            }
        });

        mButtonCurrencyList.setOnClickListener(v -> {

            Intent intent=new Intent(getContext(),ListCurrencyActivity.class);
            startActivity(intent);

        });

    }

    @Override
    public void setCurrency(String currency) {

        mButtonCurrencyList.setText(currency);
    }


}
