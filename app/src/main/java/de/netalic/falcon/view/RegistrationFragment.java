package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
    private TextInputLayout mTextInputLayout;
    private EditText mEditTextCountryName;
    private EditText mEditTextCountryCode;
    private MaskedEditText mCountryPhoneNumber;
    private View mroot;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mroot = inflater.inflate(R.layout.fragment_registration, container, false);


        initUiComponents();
        setCountryPicker();
        initListener();
        modifyCountryName();
        return mroot;
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

    }

    @Override
    public void setPresenter(RegistrationContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

    }

    private void setCountryPicker() {
        mCountryPicker = new CountryPicker.Builder()
                .with(getContext())
                .listener(new OnCountryPickerListener() {
                    @Override
                    public void onSelectCountry(Country country) {

                        mEditTextCountryName.setText(country.getName());
                        mEditTextCountryCode.setText(country.getDialCode());
                    }
                }).build();
    }

    private void initListener() {

        mEditTextCountryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mCountryPicker.showDialog(getFragmentManager());
            }
        });


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

        mCountryPhoneNumber = mroot.findViewById(R.id.edittext_registration_phonenumber);
        mTextInputLayout = mroot.findViewById(R.id.textinputlayout_registration_countrypicker);
        mEditTextCountryName = mroot.findViewById(R.id.edittext_registration_countrypicker);
        mEditTextCountryCode = mroot.findViewById(R.id.edittext_registration_code);
        mCountryPhoneNumber = mroot.findViewById(R.id.edittext_registration_phonenumber);


    }

}
