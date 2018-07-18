package de.netalic.falcon.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.RegistrationPresenter;
import de.netalic.falcon.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegistrationActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);

//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setTitle(getString(R.string.registration_toolbartitle));
//        }

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

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}