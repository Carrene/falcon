package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;

import java.util.ArrayList;
import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import de.netalic.falcon.R;
import de.netalic.falcon.presenter.RegistrationContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationFragment extends Fragment implements RegistrationContract.View {

    private RegistrationContract.Presenter mPresenter;
    private CountryPicker mCountryPicker;
    private EditText mEditTextCountryName;
    private EditText mEditTextCountryCode;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_registration, container, false);
        setHasOptionsMenu(true);
        initUiComponents();
        setCountryPicker();
        initListener();
        modifyCountryName();
        return mRoot;
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
    public void showPhoneNumberFormatError() {
        //TODO: Add error to text input layout of phone number
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
                //TODO: pass user to presenter based on input
//                mPresenter.register(user);
            }
        }
        return true;
    }

    private void setCountryPicker() {

        mCountryPicker = new CountryPicker.Builder()
                .with(getContext())
                .listener(country -> {
                    mEditTextCountryName.setText(country.getName());
                    mEditTextCountryCode.setText(country.getDialCode());
                }).build();
    }

    private void initListener() {

        mEditTextCountryName.setOnClickListener(v -> mCountryPicker.showDialog(getFragmentManager()));
    }

    private void modifyCountryName() {

        List<Country> countries = mCountryPicker.getAllCountries();
        List<Country> allCountryModified = new ArrayList<>();
        for (int i = 0; i < countries.size(); i++) {
            Country country = countries.get(i);
            if (country.getDialCode().equals("+972")) {
                continue;
            }
            if (country.getDialCode().equals("+970")) {
                continue;
            }
            allCountryModified.add(country);
        }
        mCountryPicker.setCountries(allCountryModified);
    }

    private void initUiComponents() {

        mEditTextCountryName = mRoot.findViewById(R.id.edittext_registration_countrypicker);
        mEditTextCountryCode = mRoot.findViewById(R.id.edittext_registration_code);
    }
}
