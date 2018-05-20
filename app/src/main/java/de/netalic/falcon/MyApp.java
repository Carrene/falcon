package de.netalic.falcon;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {

    private static MyApp instance;
    public static RealmConfiguration.Builder insensitiveRealmConfiguration;
    public static RealmConfiguration.Builder sensitiveRealmConfiguration;

    public static MyApp getInstance() {

        return instance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        instance = this;
        Realm.init(this);

        insensitiveRealmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1);
        sensitiveRealmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1);
    }
}