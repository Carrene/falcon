package de.netalic.falcon.network;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import de.netalic.falcon.BuildConfig;
import de.netalic.falcon.MyApp;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit sRetrofit = null;
    private static ApiInterface sApi;
    public static String sUrl;
    public static String sTestUrl;

    private static Retrofit getClient() {


        if (sRetrofit == null) {

            if (sTestUrl == null) {

                OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
                okHttpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
                ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.getInstance()));
                okHttpClient.cookieJar(cookieJar);
                sRetrofit = new Retrofit.Builder().baseUrl(getUrl())
                        .client(okHttpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } else {
                OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
                okHttpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
                sRetrofit = new Retrofit.Builder().baseUrl(getUrl())
                        .client(okHttpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }


        return sRetrofit;
    }

    public static ApiInterface getService() {

        if (sApi == null) {
            sApi = getClient().create(ApiInterface.class);
        }
        return sApi;
    }

    public static String getUrl() {

        if (sTestUrl == null) {
            return String.format("%s:%s/apiv%s/", BuildConfig.WEB_SERVICE_URL, BuildConfig.WEB_SERVICE_PORT, BuildConfig.WEB_SERVICE_VERSION);
        }
        return sTestUrl;
    }
}
