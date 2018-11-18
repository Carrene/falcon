package de.netalic.falcon.ui.charge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.common.ListCurrencySpinnerAdapter;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.util.OffsetItemDecoration;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFragment extends Fragment implements ChargeContract.View, ChargeWalletRecyclerViewAdapter.Callback {

    private View mRoot;
    private RecyclerView mRecyclerViewPaymentGateway;
    private ChargePaymentGatewayRecyclerViewAdapter mRecyclerViewAdapterChargePaymentGateway;
    private ChargeContract.Presenter mPresenter;
    private SnapHelper mPaymentGatewaySnapHelper;
    private List<Rate> mRateList;
    private ListCurrencySpinnerAdapter mListCurrencySpinnerAdapter;
    private Spinner mSpinnerCurrencyList;
    private DecimalFormat mDecimalFormat;
    private TextInputEditText mTextInputEditTextFirstAmount;
    private TextInputEditText mTextInputEditTextSecondAmount;
    private int mSelectedPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_charge, null);
        mDecimalFormat = new DecimalFormat("0.00##");
        return mRoot;
    }

    public static ChargeFragment newInstance() {

        return new ChargeFragment();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mRecyclerViewPaymentGateway = mRoot.findViewById(R.id.recyclerViewPaymentGateway);
        mRecyclerViewPaymentGateway.addItemDecoration(new OffsetItemDecoration(getContext()));

        mRecyclerViewAdapterChargePaymentGateway = new ChargePaymentGatewayRecyclerViewAdapter(new ArrayList<>());
        mRecyclerViewPaymentGateway.setAdapter(mRecyclerViewAdapterChargePaymentGateway);
        mRecyclerViewPaymentGateway.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        mPaymentGatewaySnapHelper = new LinearSnapHelper();
        mPaymentGatewaySnapHelper.attachToRecyclerView(mRecyclerViewPaymentGateway);

        initUiComponent();
        initListener();
        getWalletList();
        getListCurrency();
    }

    private void initListener() {

        mTextInputEditTextSecondAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mTextInputEditTextSecondAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")){
                        s.clear();
                    }
                    if (s.toString().equals("")) {

                        mTextInputEditTextFirstAmount.setText("");

                    } else {
                        mTextInputEditTextFirstAmount.setText(String.valueOf(mDecimalFormat.format(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy())));
                    }
                }
            }
        });


        mTextInputEditTextFirstAmount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (mTextInputEditTextFirstAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")){

                        s.clear();
                    }

                    if (s.toString().equals("")) {

                        mTextInputEditTextSecondAmount.setText("");

                    } else {

                        mTextInputEditTextSecondAmount.setText(String.valueOf(mDecimalFormat.format(Double.valueOf(s.toString()) * mRateList.get(mSelectedPosition).getBuy())));
                    }
                }
            }
        });


        mRecyclerViewPaymentGateway.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    View centerView = mPaymentGatewaySnapHelper.findSnapView(recyclerView.getLayoutManager());
                    int pos = recyclerView.getLayoutManager().getPosition(centerView);
                    ((ChargePaymentGatewayRecyclerViewAdapter) mRecyclerViewPaymentGateway.getAdapter()).select(pos);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });

        mSpinnerCurrencyList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mSelectedPosition = position;
                if (mTextInputEditTextSecondAmount.getText().toString().equals("") && mTextInputEditTextFirstAmount.getText().toString().equals("")) {


                } else if (mTextInputEditTextSecondAmount.getText().toString().equals("")) {
                    mTextInputEditTextSecondAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextFirstAmount.getText().toString()) * mRateList.get(position).getBuy()))));
                } else if (mTextInputEditTextFirstAmount.getText().toString().equals("")) {

                    mTextInputEditTextFirstAmount.setText((String.valueOf(mDecimalFormat.format(Double.valueOf(mTextInputEditTextSecondAmount.getText().toString()) * mRateList.get(position).getBuy()))));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                mSelectedPosition = 1;
            }
        });
    }

    private void initUiComponent() {

        mSpinnerCurrencyList = mRoot.findViewById(R.id.spinner_load_spinner);
        mTextInputEditTextFirstAmount = mRoot.findViewById(R.id.edittext_charge_firstamount);
        mTextInputEditTextSecondAmount = mRoot.findViewById(R.id.edittext_charge_secondeamount);
    }

    public void getWalletList() {

        mPresenter.getWalletList();
    }

    private void getListCurrency() {

        mPresenter.listExchangeRate();
    }

    @Override
    public void setRateList(List<Rate> rateList) {

        mRateList = rateList;
        mListCurrencySpinnerAdapter = new ListCurrencySpinnerAdapter(getContext(), mRateList);
        mSpinnerCurrencyList.setAdapter(mListCurrencySpinnerAdapter);
    }

    @Override
    public void setListWallet(List<Wallet> dataList) {

        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.charge_braintreelogo);
        mRecyclerViewAdapterChargePaymentGateway.setDataSource(list);
    }

    @Override
    public void setPresenter(ChargeContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
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

    @Override
    public void navigationToAddWallet() {

        Intent intent = new Intent(getContext(), AddWalletActivity.class);
        startActivity(intent);
    }
}
