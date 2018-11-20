package de.netalic.falcon.ui.addwallet;

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
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.SnackbarUtil;

public class AddWalletFragment extends Fragment implements AddWalletContract.View {

    private AddWalletContract.Presenter mPresenter;
    private View mRoot;
    private Button mButtonCurrencyList;
    private EditText mEditTextWalletName;
    private Button mButtonAddWallet;
    public static final String SELECTED_CURRENCY = "selectedCurrency";
    private Wallet mNewWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_addwallet, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        initUiListener();

    }

    @Override
    public void setPresenter(AddWalletContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        ((BaseActivity) getActivity()).showMaterialDialog();
    }

    @Override
    public void dismissProgressBar() {

        ((BaseActivity) getActivity()).dismissMaterialDialog();

    }

    public static AddWalletFragment newInstance(String currency) {

        Bundle bundle = new Bundle();
        bundle.putString(AddWalletFragment.SELECTED_CURRENCY, currency);
        AddWalletFragment fragment = new AddWalletFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initUiComponent() {

        mButtonCurrencyList = mRoot.findViewById(R.id.butten_addwallet_navigationtocurrencieslist);
        mEditTextWalletName = mRoot.findViewById(R.id.edittext_addwallet_walletname);
        mButtonAddWallet = mRoot.findViewById(R.id.button_addwallet_addwalletrequest);

    }

    private void initUiListener() {

        mButtonAddWallet.setOnClickListener(v -> {

            if (mEditTextWalletName.getText().toString().equals("") || mButtonCurrencyList.getText().toString().equals(getContext().getString(R.string.addwallet_pleaseselectcurrency))) {

                SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_pleasefillbox), getContext());
            } else {

                addWallet();
            }
        });

        mButtonCurrencyList.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ListCurrencyActivity.class);
            intent.putExtra(SELECTED_CURRENCY, mButtonCurrencyList.getText().toString());

            startActivityForResult(intent, 1);

        });

    }

    @Override
    public void onResume() {
        super.onResume();

        String currency=((AddWalletActivity)getActivity()).getCurrency();
        if (currency==null){
            mButtonCurrencyList.setText(getContext().getString(R.string.addwallet_pleaseselectcurrency));
        }
        else {
            mButtonCurrencyList.setText(currency);
        }

    }


    @Override
    public void setWallet(Wallet wallet) {

        mNewWallet=wallet;
        Intent intent=new Intent(getContext(),DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void errorWalletNameAlreadyExist() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.addwallet_walletnamealreadyexist),getContext());
    }

    @Override
    public void errorWalletWithThisCurrencyAlreadyExist() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.addwallet_walletwiththiscurrencyalreadyexist),getContext());
    }

    @Override
    public void errorInvalidCurrencyCode() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.addwallet_invalidcurrencycode),getContext());
    }

    @Override
    public void errorInvalidWalletName() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.addwallet_invalidwalletname),getContext());
    }

    @Override
    public void errorAddWalletAsAnAnonymous() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.addwallet_addwalletasananonymous),getContext());
    }

    private void addWallet(){

        mPresenter.addWallet(mEditTextWalletName.getText().toString(),mButtonCurrencyList.getText().toString());
    }
}
