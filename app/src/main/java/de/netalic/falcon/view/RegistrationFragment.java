package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.mukesh.countrypicker.Country;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.OnCountryPickerListener;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.RegistrationContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationFragment extends Fragment implements RegistrationContract.View {

    private RegistrationContract.Presenter mPresenter;
    private CountryPicker mCountryPicker;
    private Button btButton;
    private EditText etEditText;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_registration, container, false);
        etEditText = root.findViewById(R.id.editText2);
        btButton = root.findViewById(R.id.button3);
        setCountryPicker();
        initListenre();
        modifyCountryName();
        return root;
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

    public void setCountryPicker() {
        mCountryPicker =
                new CountryPicker.Builder().with(getContext())
                        .listener(new OnCountryPickerListener() {
                            @Override
                            public void onSelectCountry(Country country) {
                                //DO something here

                                etEditText.setText(country.getDialCode());
                            }
                        })
                        .build();
    }

    public void initListenre() {

        btButton.setOnClickListener(new View.OnClickListener() {
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

}
