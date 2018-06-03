package de.netalic.falcon.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.RegistrationPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class RegistrationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = findViewById(R.id.toolbar_registration);
        setSupportActionBar(toolbar);

        RegistrationFragment registrationFragment = (RegistrationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_registration_fragmentcontainer);
        if (registrationFragment == null) {
            registrationFragment = RegistrationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), registrationFragment, R.id.framelayout_registration_fragmentcontainer);
        }

        new RegistrationPresenter(registrationFragment);

    }
}