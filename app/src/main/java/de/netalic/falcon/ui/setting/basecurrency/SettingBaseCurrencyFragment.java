package de.netalic.falcon.ui.setting.basecurrency;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;

public class SettingBaseCurrencyFragment extends Fragment implements SettingBaseCurrencyContract.View {

    private SettingBaseCurrencyContract.Presenter mBaseCurrencyPresenter;
    private View mRoot;
    private RecyclerView mRecyclerViewCurrencyList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_basecurrency, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
    }

    @Override
    public void setPresenter(SettingBaseCurrencyContract.Presenter presenter) {

        mBaseCurrencyPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        ((BaseActivity) getActivity()).showMaterialDialog();

    }

    @Override
    public void dismissProgressBar() {


        ((BaseActivity) getActivity()).dismissMaterialDialog();
    }

    private void initUiComponent() {

        mRecyclerViewCurrencyList = mRoot.findViewById(R.id.recyclerview_basecurrency_currencylist);

    }

    public static SettingBaseCurrencyFragment newInstance() {

        Bundle args = new Bundle();

        SettingBaseCurrencyFragment fragment = new SettingBaseCurrencyFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
