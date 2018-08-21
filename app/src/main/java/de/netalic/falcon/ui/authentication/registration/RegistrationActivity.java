package de.netalic.falcon.ui.authentication.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class RegistrationActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        RegistrationFragment registrationFragment = (RegistrationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_registration_fragmentcontainer);
        if (registrationFragment == null) {
            registrationFragment = RegistrationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), registrationFragment, R.id.framelayout_registration_fragmentcontainer);
        }

        new RegistrationPresenter(registrationFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_registration;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.registration_toolbartitle);
    }
}