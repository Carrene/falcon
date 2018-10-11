package de.netalic.falcon.ui.setting.basecurrency;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class SettingBaseCurrencyFragment extends Fragment implements SettingBaseCurrencyContract.View,CurrenciesRecyclerViewAdapter.Callback {

    private SettingBaseCurrencyContract.Presenter mBaseCurrencyPresenter;
    private View mRoot;
    private RecyclerView mRecyclerViewCurrencyList;
    private List<Currency> mCurrencyList;
    private String mBaseCurrency;
    private CurrenciesRecyclerViewAdapter mCurrenciesRecyclerViewAdapter;
    private String mUpdateBaseCurrency;
    private Map<String,Object>mTokenBody;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_basecurrency, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor=new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        mTokenBody=Parser.getTokenBody(sharedPreferencesJwtPersistor.get());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCurrencyList.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),layoutManager.getOrientation());
        mRecyclerViewCurrencyList.addItemDecoration(dividerItemDecoration);

        mCurrenciesRecyclerViewAdapter=new CurrenciesRecyclerViewAdapter(mCurrencyList,mBaseCurrency);
        mRecyclerViewCurrencyList.setAdapter(mCurrenciesRecyclerViewAdapter);

        getCurrencyList();
        initListener();

    }

    @Override
    public void setPresenter(SettingBaseCurrencyContract.Presenter presenter) {

        mBaseCurrencyPresenter = presenter;
    }
    private void getBaseCurrency(){

        mBaseCurrencyPresenter.getBaseCurrency();
    }

    @Override
    public void showProgressBar() {

        ((BaseActivity) getActivity()).showMaterialDialog();

    }

    private void initListener(){


    }

    @Override
    public void dismissProgressBar() {


        ((BaseActivity) getActivity()).dismissMaterialDialog();
    }

    private void initUiComponent() {

        mRecyclerViewCurrencyList = mRoot.findViewById(R.id.recyclerview_basecurrency_currencylist);

    }

    public static SettingBaseCurrencyFragment newInstance() {

        Bundle bundle = new Bundle();

        SettingBaseCurrencyFragment fragment = new SettingBaseCurrencyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setCurrencyList(List<Currency> currencyList) {

        mCurrencyList = currencyList;

        getBaseCurrency();

    }

    @Override
    public void updateBaseCurrency(User user) {

        mBaseCurrencyPresenter.updateUser(user);
        mBaseCurrency = user.getBaseCurrency();
    }

    @Override
    public void baseCurrencyCodeMissingInForm() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.basecurrency_basecurrencycodemissinginform),getContext());
    }

    @Override
    public void clientNotFoundOrClientIdIsInvalid() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.basecurrency_clientnotfoundorclientidisinvalid),getContext());
    }

    @Override
    public void invalidCurrencyCode() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.basecurrency_invalidcurrencycode),getContext());
    }

    @Override
    public void tryingToPassWithUnauthorizedMember() {

        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.basecurrency_tryingtopasswithunauthorizedmember),getContext());
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {

        mBaseCurrency=baseCurrency;
    }

    @Override
    public void updateUser(User user) {

        mUpdateBaseCurrency=user.getBaseCurrency();
    }

    private void getCurrencyList() {

        mBaseCurrencyPresenter.getCurrencyList();
    }

    @Override
    public void changeBaseCurrency(String currencyCode) {

        mBaseCurrencyPresenter.changeBaseCurrency((Integer) mTokenBody.get("id"),currencyCode);
    }
}
