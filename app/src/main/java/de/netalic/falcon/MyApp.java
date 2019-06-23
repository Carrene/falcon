package de.netalic.falcon;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.authentication.AuthenticationRoomRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.currency.CurrencyRepository;
import de.netalic.falcon.data.repository.currency.CurrencyRestRepository;
import de.netalic.falcon.data.repository.rate.RateRepository;
import de.netalic.falcon.data.repository.rate.RateRestRepository;
import de.netalic.falcon.data.repository.receipt.ReceiptRepository;
import de.netalic.falcon.data.repository.receipt.ReceiptRestRepository;
import de.netalic.falcon.data.repository.receipt.ReceiptRoomRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRestRepository;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.data.repository.user.UserRestRepository;
import de.netalic.falcon.data.repository.user.UserRoomRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;
import de.netalic.falcon.data.repository.wallet.WalletRestRepository;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApp extends Application {

    private static MyApp sInstance;

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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto_medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        RepositoryLocator.getInstance().setRepository(new UserRepository(new UserRestRepository(), new UserRoomRepository(this)));
        RepositoryLocator.getInstance().setRepository(new WalletRepository(new WalletRestRepository(), null));
        RepositoryLocator.getInstance().setRepository(new AuthenticationRepository(null, new AuthenticationRoomRepository(this)));
        RepositoryLocator.getInstance().setRepository(new RateRepository(new RateRestRepository(), null));
        RepositoryLocator.getInstance().setRepository(new ReceiptRepository(new ReceiptRestRepository(), new ReceiptRoomRepository(this)));
        RepositoryLocator.getInstance().setRepository(new TransactionRepository(new TransactionRestRepository(), null));
        RepositoryLocator.getInstance().setRepository(new CurrencyRepository(new CurrencyRestRepository(), null));
    }
}