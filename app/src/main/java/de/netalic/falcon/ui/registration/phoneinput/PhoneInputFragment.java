package de.netalic.falcon.ui.registration.phoneinput;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ehsanmashhadi.library.view.CountryPicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.registration.phoneconfirmation.PhoneConfirmationActivity;
import de.netalic.falcon.util.CustomPhoneFormatTextWatcher;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;


public class PhoneInputFragment extends Fragment implements PhoneInputContract.View {

    private PhoneInputContract.Presenter mPresenter;
    private CountryPicker mCountryPicker;
    private TextInputEditText mTextInputEditTextCountryName;
    private TextInputEditText mTextInputEditTextCountryCode;
    private TextInputEditText mTextInputEditTextPhone;
    private View mRoot;
    private CustomPhoneFormatTextWatcher mCustomPhoneFormatTextWatcher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_phoneinput, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initListener();
        formatPhoneNumber();
    }

    public static PhoneInputFragment newInstance() {

        return new PhoneInputFragment();
    }

    @Override
    public void onResume() {

        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(PhoneInputContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_phoneinput_toolbar, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_phoneinput_done: {

                Keyboard.hideKeyboard(mRoot);
                if (mTextInputEditTextCountryCode.getText().toString().matches("")) {

                    showCountryCodeError();
                } else if (mTextInputEditTextPhone.getText().toString().equals("")) {

                    checkNotNull(getContext());
                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.registration_pleasefillyournumber), getContext());
                } else {
                    claim();
                }
            }
        }
        return true;
    }

    private void showCountryCodeError() {

        TextInputLayout textInputLayout = mRoot.findViewById(R.id.textinputlayout_phoneinput_countrypicker);
        textInputLayout.setError(getContext().getString(R.string.registration_pleasepickcountry));
    }

    private void claim() {

        User user = new User(mTextInputEditTextCountryCode.getText().toString() + PhoneNumberUtils.stripSeparators(mTextInputEditTextPhone.getText().toString()));
        mPresenter.claim(user);
    }

    private void initListener() {

        mTextInputEditTextCountryName.setOnClickListener(v -> {

            List<String> exceptCountries = new ArrayList<>();
            mCountryPicker = new com.ehsanmashhadi.library.view.CountryPicker.Builder(getContext()).showingDialCode(false)
                    .setLocale(new Locale("EN")).showingFlag(true)
                    .sortBy(com.ehsanmashhadi.library.view.CountryPicker.Sort.COUNTRY).exceptCountriesName(exceptCountries)
                    .setViewType(com.ehsanmashhadi.library.view.CountryPicker.ViewType.BOTTOMSHEET)
                    .enablingSearch(true).setCountrySelectionListener(country -> {
                        mTextInputEditTextCountryName.setText(country.getName());
                        mTextInputEditTextCountryCode.setText(country.getDialCode());

                    })
                    .setPreSelectedCountry("Iran")
                    .setStyle(R.style.CountryPickerLightStyle)
                    .build();
            mCountryPicker.show((AppCompatActivity) getActivity());
        });

        mTextInputEditTextPhone.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE) {

                claim();
            }
            return false;
        });
    }

    private void initUiComponents() {

        mTextInputEditTextCountryName = mRoot.findViewById(R.id.edittext_phoneinput_countrypicker);
        mTextInputEditTextCountryCode = mRoot.findViewById(R.id.edittext_phoneinput_code);
        mTextInputEditTextPhone = mRoot.findViewById(R.id.edittext_phoneinput_phonenumber);
        setHasOptionsMenu(true);
    }

    private void formatPhoneNumber() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mCustomPhoneFormatTextWatcher != null)
                mTextInputEditTextPhone.removeTextChangedListener(mCustomPhoneFormatTextWatcher);
                mCustomPhoneFormatTextWatcher = new CustomPhoneFormatTextWatcher("IR", "+98");
                mTextInputEditTextPhone.addTextChangedListener(mCustomPhoneFormatTextWatcher);
        }
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
    public void showErrorInvalidUdidOrPhone() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.phoneconfirmation_invalidudidorphone), getContext());
    }
}
