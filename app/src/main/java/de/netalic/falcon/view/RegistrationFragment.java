package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.RegistrationContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationFragment extends Fragment implements RegistrationContract.View {

    private RegistrationContract.Presenter mPresenter;
    private CountryPicker mCountryPicker;
    private EditText mEditTextCountryName;
    private EditText mEditTextCountryCode;
    private MaskedEditText mEditTextPhone;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_registration, container, false);
        initUiComponents();
        setCountryPicker();
        initListener();
        modifyCountriesName();
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
                if (mEditTextCountryCode.getText().toString().equals("")) {
                    showCountryCodeError();
                    break;
                }
                User user = new User(mEditTextCountryCode.getText().toString() + mEditTextPhone.getRawText());
                mPresenter.claim(user);
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
                }).build();
    }

    private void showCountryCodeError() {
        //TODO: Milad display error on country code picker input layout error
    }

    //TODO: Milad call claim from repository when user click on tick icon, Get help from UserRepositoryTest
    private void claim() {

    }

    private void initListener() {

        mEditTextCountryName.setOnClickListener(v -> mCountryPicker.showDialog(checkNotNull(getFragmentManager())));
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
        mCountryPicker.setCountries(allCountryModified);
    }

    private void initUiComponents() {

        mEditTextCountryName = mRoot.findViewById(R.id.edittext_registration_countrypicker);
        mEditTextCountryCode = mRoot.findViewById(R.id.edittext_registration_code);
        mEditTextPhone = mRoot.findViewById(R.id.edittext_registration_phonenumber);
        setHasOptionsMenu(true);
    }
}
