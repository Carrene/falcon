package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.repository.authentication.AuthenticationRepository;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        AtomicReference<Intent> intent = new AtomicReference<>();

        if (sharedPreferencesJwtPersistor.get() == null) {
            intent.set(new Intent(this, RegistrationActivity.class));

        } else {
            AuthenticationRepository.getInstance().get(deal -> {
                if (deal.getModel() == null) {
                    intent.set(new Intent(this, AuthenticationDefinitionActivity.class));
                } else {
                    intent.set(new Intent(this, DashboardActivity.class));
                }
            });
        }
        intent.set(new Intent(this, DashboardActivity.class));

        startActivity(intent.get());
        finish();
    }
}
