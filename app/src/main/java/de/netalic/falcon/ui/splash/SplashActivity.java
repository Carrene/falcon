package de.netalic.falcon.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.ui.registration.authentication.AuthenticationActivity;
import de.netalic.falcon.ui.registration.authnticationdefinition.AuthenticationDefinitionActivity;
import de.netalic.falcon.ui.registration.phoneinput.PhoneInputActivity;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
        AtomicReference<Intent> intent = new AtomicReference<>();

        if (sharedPreferencesJwtPersistor.get() == null) {
            intent.set(new Intent(this, PhoneInputActivity.class));

        } else {
            RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).get(deal -> {
                if (deal.getModel() == null) {

                    intent.set(new Intent(this, AuthenticationDefinitionActivity.class));

                } else {
                    intent.set(new Intent(this, AuthenticationActivity.class));
                }
            });
        }

        startActivity(intent.get());
        finish();
    }
}
