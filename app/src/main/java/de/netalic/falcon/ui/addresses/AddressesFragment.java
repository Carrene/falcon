package de.netalic.falcon.ui.addresses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;

public class AddressesFragment extends Fragment implements AddressesContract.View {


    private View mRoot;
    private AddressesContract.Presenter mAddressesPresenter;
    private RecyclerView mRecyclerViewWalletAddress;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_addresses,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
    }

    @Override
    public void setPresenter(AddressesContract.Presenter presenter) {
        mAddressesPresenter=presenter;

    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static AddressesFragment newInstance() {

        AddressesFragment fragment = new AddressesFragment();
        return fragment;
    }

    private void initUiComponent(){

        mRecyclerViewWalletAddress=mRoot.findViewById(R.id.recyclerview_addresses_walletlist);
    }
}
