package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.SpinnerAdapter;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.ChargeContract;
import de.netalic.falcon.util.MaterialDialogUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFragment extends Fragment implements ChargeContract.View {

    private ChargeContract.Presenter mChargePresenter;
    private View mRoot;
    private Spinner mSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_charge,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        getListWallet();

    }

    @Override
    public void setPresenter(ChargeContract.Presenter presenter) {

        mChargePresenter= checkNotNull(presenter);

    }

    public static  ChargeFragment newInstance(){

        return new ChargeFragment();
    }

    public void initUiComponent(){

        mSpinner=mRoot.findViewById(R.id.spinner_charge_customspinner);

    }

    public void setWalletToSpinner(List<Wallet> wallets){

        SpinnerAdapter spinnerAdapter=new SpinnerAdapter(getContext(),wallets);
        mSpinner.setAdapter(spinnerAdapter);
    }

    @Override
    public void showProgressBar() {
        checkNotNull(getContext());
        MaterialDialogUtil.showMaterialDialog(getContext());
    }

    @Override
    public void dismissProgressBar() {

        MaterialDialogUtil.dismissMaterialDialog();
    }

    @Override
    public void setListWallet(List<Wallet> walletList) {

        Integer []items=new Integer[walletList.size()];
        for (int i=0;i<walletList.size();i++){

            items[i]=walletList.get(i).getId();
        }
        setWalletToSpinner(walletList);

    }
    public void getListWallet(){

        mChargePresenter.getWalletList();
    }
}
