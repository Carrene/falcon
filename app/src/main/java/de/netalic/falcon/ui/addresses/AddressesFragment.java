package de.netalic.falcon.ui.addresses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.addwallet.AddWalletActivity;
import de.netalic.falcon.ui.base.BaseActivity;

public class AddressesFragment extends Fragment implements AddressesContract.View {


    private View mRoot;
    private AddressesContract.Presenter mAddressesPresenter;
    private RecyclerView mRecyclerViewWalletAddress;
    private List<Wallet>mWalletList;
    private AddressesRecyclerViewAdapter mAddressesRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mRoot=inflater.inflate(R.layout.fragment_addresses,null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getWalletList();
        initUiComponent();
    }

    @Override
    public void setPresenter(AddressesContract.Presenter presenter) {
        mAddressesPresenter=presenter;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_walletlist_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        navigateToAddWalletActivity();
        return true;
    }

    private void navigateToAddWalletActivity() {
        Intent intent = new Intent(getContext(), AddWalletActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgressBar() {

        ((BaseActivity)getActivity()).showMaterialDialog();
    }

    @Override
    public void dismissProgressBar() {

        ((BaseActivity)getActivity()).dismissMaterialDialog();
    }


    public static AddressesFragment newInstance() {

        AddressesFragment fragment = new AddressesFragment();
        return fragment;
    }

    private void initUiComponent(){

        mRecyclerViewWalletAddress=mRoot.findViewById(R.id.recyclerview_addresses_walletlist);

    }

    private void getWalletList(){

        mAddressesPresenter.getWalletList();

    }

    @Override
    public void setWalletList(List<Wallet> walletList) {

        mWalletList=walletList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewWalletAddress.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),layoutManager.getOrientation());
        mRecyclerViewWalletAddress.addItemDecoration(dividerItemDecoration);

        mAddressesRecyclerViewAdapter=new AddressesRecyclerViewAdapter(mWalletList,getContext());
        mRecyclerViewWalletAddress.setAdapter(mAddressesRecyclerViewAdapter);

    }

}
