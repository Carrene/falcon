package de.netalic.falcon.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.google.gson.JsonObject;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.adapter.SpinnerAdapter;
import de.netalic.falcon.model.User;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.presenter.ChargeContract;
import de.netalic.falcon.util.MaterialDialogUtil;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeFragment extends Fragment implements ChargeContract.View {

    private ChargeContract.Presenter mChargePresenter;
    private View mRoot;
    private Spinner mSpinner;
    public static final String ARGUMENT_USER = "USER";
    private Button mPaymentButton;
    private EditText mAmountEditText;
    private SpinnerAdapter mSpinnerAdapter;
    private User mUser;
    private static final int DROP_IN_REQUEST = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_charge, null);
        mUser = getArguments().getParcelable(ARGUMENT_USER);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        getListWallet();
        initListener();
        setHasOptionsMenu(true);
    }

    @Override
    public void setPresenter(ChargeContract.Presenter presenter) {

        mChargePresenter = checkNotNull(presenter);
    }

    public static ChargeFragment newInstance(User user) {

        ChargeFragment fragment = new ChargeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initUiComponent() {

        mSpinner = mRoot.findViewById(R.id.spinner_charge_customspinner);
        mPaymentButton = mRoot.findViewById(R.id.button_charge_payment);
        mAmountEditText = mRoot.findViewById(R.id.edittext_charge_amount);

    }

    public void setWalletToSpinner(List<Wallet> wallets) {

        mSpinnerAdapter = new SpinnerAdapter(getContext(), wallets);
        mSpinner.setAdapter(mSpinnerAdapter);
    }

    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
        MaterialDialogUtil.getInstance().showMaterialDialog(getContext());
    }

    @Override
    public void dismissProgressBar() {

        MaterialDialogUtil.getInstance().dismissMaterialDialog();
    }

    @Override
    public void setListWallet(List<Wallet> walletList) {

        setWalletToSpinner(walletList);
    }

    @Override
    public void setToken(JsonObject token) {

        String braintreeToken = token.get("braintreeToken").getAsString();
        String chargeDataToken = token.get("chargeDataToken").getAsString();

        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(braintreeToken);
        startActivityForResult(dropInRequest.getIntent(getContext()), DROP_IN_REQUEST);
    }

    @Override
    public void showErrorInvalidAmount() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_invalidamount), getContext());
    }

    @Override
    public void showErrorInvalidWalletId() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_invalidwalletid), getContext());
    }

    @Override
    public void showErrorAmountIsSmallerThanLowerBound() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_amountissmallerthanlowerbound), getContext());
    }

    @Override
    public void showErrorAmountIsGreaterThanUpperBound() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_amountisgreaterthanupperbound), getContext());
    }

    public void getListWallet() {

        mChargePresenter.getWalletList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_charge_toolbar, menu);
    }

    public void initListener() {

        mPaymentButton.setOnClickListener(v -> {

                    Keyboard.hideKeyboard(mRoot);
                    if (mAmountEditText.getText().toString().equals("")) {

                        checkNotNull(getContext());
                        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.charge_pleasefillamount), getContext());

                    } else {
                        Wallet wallet = mSpinnerAdapter.getItem(mSpinner.getSelectedItemPosition());
                        mChargePresenter.charge(wallet.getId(), Double.parseDouble(mAmountEditText.getText().toString()));
                    }
                }
        );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DROP_IN_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {

                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentMethodNonce = result.getPaymentMethodNonce().getNonce();
                // send paymentMethodNonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                //TODO: Payment is cancelled
                // canceled
            } else {
                // an error occurred, checked the returned exception
                Exception exception = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }
}
