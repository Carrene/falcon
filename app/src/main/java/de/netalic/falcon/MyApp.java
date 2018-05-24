package de.netalic.falcon;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApp extends Application {

    private static MyApp sInstance;
    public static RealmConfiguration.Builder sInsensitiveRealmConfiguration;
    public static RealmConfiguration.Builder sSensitiveRealmConfiguration;

    public static MyApp getInstance() {

        return sInstance;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        sInstance = this;
        Realm.init(this);

        sInsensitiveRealmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1);
        sSensitiveRealmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1);
    }
}