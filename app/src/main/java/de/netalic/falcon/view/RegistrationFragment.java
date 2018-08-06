package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.RegistrationContract;
import de.netalic.falcon.util.MaterialDialogUtil;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;


public class RegistrationFragment extends Fragment implements RegistrationContract.View {

    private RegistrationContract.Presenter mPresenter;
    private CountryPicker mCountryPicker;
    private EditText mEditTextCountryName;
    private EditText mEditTextCountryCode;
    private EditText mEditTextPhone;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_registration, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        setCountryPicker();
        initListener();
        modifyCountriesName();
    }

    public static RegistrationFragment newInstance() {

        return new RegistrationFragment();
    }


    @Override
    public void onResume() {

        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(RegistrationContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_registration_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.menu_registration_done: {

                Keyboard.hideKeyboard(mRoot);
                if (mEditTextCountryCode.getText().toString().matches("XXXX")) {

                    showCountryCodeError();
                } else if (mEditTextPhone.getText().toString().equals("")) {

                    checkNotNull(getContext());
                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.registration_pleasefillyournumber), getContext());
                } else {
                    claim();
                }
            }
        }
        return true;
    }

    private void setCountryPicker() {

        mCountryPicker = new CountryPicker.Builder()
                .with(checkNotNull(getContext()))
                .listener(country -> {
                    mEditTextCountryName.setText(country.getName());
                    mEditTextCountryCode.setText(country.getDialCode());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (country.getCode().equals("IR")) {
                            mEditTextPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher("US"));

                        }
                        mEditTextPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(country.getCode()));
                    }

                }).build();
    }

    private void showCountryCodeError() {

        TextInputLayout textInputLayout = mRoot.findViewById(R.id.textinputlayout_registration_countrypicker);
        textInputLayout.setError(getContext().getString(R.string.registration_pleasepickcountry));
    }

    private void claim() {

        User user = new User(mEditTextCountryCode.getText().toString() + PhoneNumberUtils.stripSeparators(mEditTextPhone.getText().toString()));
        mPresenter.claim(user);

    }

    private void initListener() {

        mEditTextCountryName.setOnClickListener(v -> mCountryPicker.showDialog(checkNotNull(getFragmentManager())));

        mEditTextPhone.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId== EditorInfo.IME_ACTION_DONE){

                claim();
            }
            return false;
        });
    }

    private void modifyCountriesName() {

        List<Country> countries = mCountryPicker.getAllCountries();
        List<Country> allCountryModified = new ArrayList<>();
        for (Country country : countries) {
            if (country.getDialCode().equals("+972") || country.getDialCode().equals("+970")) {
                continue;
            }
            allCountryModified.add(country);
        }
        Collections.sort(allCountryModified, new CountryPicker.NameComparator());
        mCountryPicker.setCountries(allCountryModified);
    }

    private void initUiComponents() {

        mEditTextCountryName = mRoot.findViewById(R.id.edittext_registration_countrypicker);
        mEditTextCountryCode = mRoot.findViewById(R.id.edittext_registration_code);
        mEditTextPhone = mRoot.findViewById(R.id.edittext_registration_phonenumber);
        setHasOptionsMenu(true);
    }


    @Override
    public void navigationToPhoneConfirmation(User user) {

        Intent intent = new Intent(getActivity(), PhoneConfirmationActivity.class);
        intent.putExtra(PhoneConfirmationActivity.ARGUMENT_USER, user);
        startActivity(intent);

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
    public void showErrorInvalidUdidOrPhone() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.phoneconfirmation_invalidudidorphone), getContext());
    }

}
