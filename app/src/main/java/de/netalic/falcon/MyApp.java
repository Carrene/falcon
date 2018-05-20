package de.netalic.falcon;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {

    static MyApp instance;

    public static MyApp getInstance() {

        return instance;
    }

    @Override
    public void onCreate() {


        super.onCreate();
        instance = this;
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
