package de.netalic.falcon.ui.transfer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransferAmountFragment extends Fragment implements TransferAmountContract.View {

    private TransferAmountContract.Presenter mTransferAmountPresenter;
    private View mRoot;
    private EditText mEditTextWalletAmount;
    private EditText mEditTextBaseCurrency;
    private TextView mTextViewUseMaximum;
    private TextView mTextViewUseMinimum;
    private Rate mRate;
    private DecimalFormat mDecimalFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_transferamount, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUiComponent();
        initListener();
        mRate = new Rate("USD");
        mDecimalFormat = new DecimalFormat("0.00##");
        getRate();
    }

    private void initUiComponent() {

        mEditTextWalletAmount = mRoot.findViewById(R.id.edittext_transferamount_amountwallet);
        mEditTextBaseCurrency = mRoot.findViewById(R.id.edittext_transferamount_amountbase);
        mTextViewUseMinimum = mRoot.findViewById(R.id.textview_transferamount_useminimum);
        mTextViewUseMaximum = mRoot.findViewById(R.id.textview_transferamount_usemaximum);

    }

    public void getRate() {

        mTransferAmountPresenter.exchangeRate(mRate);
    }


    @Override
    public void setPresenter(TransferAmountContract.Presenter presenter) {

        mTransferAmountPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static TransferAmountFragment newInstance() {

        return new TransferAmountFragment();
    }

    @Override
    public void showErrorInvalidCurrency() {
        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferamount_invalidcurrency), getContext());
    }

    @Override
    public void showErrorRatesDoesNotExists() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.transferamount_ratedosenotexists), getContext());
    }

    @Override
    public void updateExchangeRateCurrency(Rate rate) {

        mRate = rate;
    }


    private void initListener() {


        mEditTextWalletAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mEditTextWalletAmount.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {
                        mEditTextBaseCurrency.setText("");

                    } else {

                        double amountEnter = Double.parseDouble(s.toString());
                        double rate = mRate.getBuy();
                        double dollar = amountEnter * rate;
                        String roundDollar = mDecimalFormat.format(dollar);


                        mEditTextBaseCurrency.setText(String.valueOf(roundDollar));

                    }
                }
            }
        });

        mEditTextBaseCurrency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                if (mEditTextBaseCurrency.isFocused()) {

                    if (s.toString().length() == 1 && s.toString().equals(".")) {
                        s.clear();
                    }
                    if (s.toString().equals("")) {
                        mEditTextWalletAmount.setText("");
                    } else {


                        double dollar = Double.parseDouble(s.toString());
                        double rate = mRate.getBuy();
                        double alpha = dollar / rate;
                        String roundAlpha = mDecimalFormat.format(alpha);
                        mEditTextWalletAmount.setText(roundAlpha);

                    }
                }

            }
        });

        mTextViewUseMinimum.setOnClickListener(v -> {

            mEditTextWalletAmount.requestFocus();
            mEditTextWalletAmount.setText("1");


        });

        mTextViewUseMaximum.setOnClickListener(v -> {

            mEditTextWalletAmount.requestFocus();
            mEditTextWalletAmount.setText("2500");


        });

    }


}
