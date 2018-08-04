package de.netalic.falcon;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        sInstance = this;
        Realm.init(this);

        sInsensitiveRealmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1);
        sSensitiveRealmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(1);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}