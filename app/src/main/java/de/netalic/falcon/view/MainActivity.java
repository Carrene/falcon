package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.repository.authentication.AuthenticationRepository;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initStartup();

    }


    private void initStartup() {

//        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
//        AtomicReference<Intent> intent = new AtomicReference<>();
//
//        if (sharedPreferencesJwtPersistor.get() == null) {
//            intent.set(new Intent(this, SplashActivity.class));
//
//        } else {
//            AuthenticationRepository.getInstance().get(deal -> {
//                if (deal.getModel() == null) {
//                    intent.set(new Intent(this, AuthenticationDefinitionActivity.class));
//                } else {
//                    intent.set(new Intent(this, DashboardActivity.class));
//                }
//            });
//        }
        Intent intent = new Intent(this, ChargeActivityAmount.class);
        startActivity(intent);
//        intent.get().setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent.get());

    }
}