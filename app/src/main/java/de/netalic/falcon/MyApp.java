package de.netalic.falcon;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.squareup.leakcanary.LeakCanary;

import de.AppDatabase;
import de.netalic.falcon.data.repository.authentication.AuthenticationRealmRepository;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.currency.CurrencyRepository;
import de.netalic.falcon.data.repository.currency.CurrencyRestRepository;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.rate.RateRestRepository;
import de.netalic.falcon.data.repository.receipt.ReceiptRealmRepository;
import de.netalic.falcon.data.repository.receipt.ReceiptRepository;
import de.netalic.falcon.data.repository.receipt.ReceiptRestRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRestRepository;
import de.netalic.falcon.data.repository.user.UserRealmRepository;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.data.repository.user.UserRestRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;
import de.netalic.falcon.data.repository.wallet.WalletRestRepository;
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

        AppDatabase appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "miladtest").build();


        RepositoryLocator.getInstance().setRepository(new UserRepository(new UserRestRepository(), new UserRealmRepository()));
        RepositoryLocator.getInstance().setRepository(new WalletRepository(new WalletRestRepository(), null));
        RepositoryLocator.getInstance().setRepository(new AuthenticationRepository(null, new AuthenticationRealmRepository()));
        RepositoryLocator.getInstance().setRepository(new RateRepository(new RateRestRepository(), null));
        RepositoryLocator.getInstance().setRepository(new ReceiptRepository(new ReceiptRestRepository(), new ReceiptRealmRepository()));
        RepositoryLocator.getInstance().setRepository(new TransactionRepository(new TransactionRestRepository(), null));
        RepositoryLocator.getInstance().setRepository(new CurrencyRepository(new CurrencyRestRepository(), null));
    }
}