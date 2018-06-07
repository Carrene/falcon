package de.netalic.falcon.network;

import java.util.concurrent.TimeUnit;

import de.netalic.falcon.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit sRetrofit = null;
    private static ApiInterface sApi;
    public static String sUrl;

    private static Retrofit getClient(String baseURL) {

        if (sRetrofit == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
            okHttpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
//            ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.getInstance()));
//            okHttpClient.cookieJar(cookieJar);
            sRetrofit = new Retrofit.Builder().baseUrl(baseURL)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static ApiInterface getService() {

        if (sApi == null) {

            sApi = getClient(getUrl()).create(ApiInterface.class);
        }
        return sApi;
    }

    public static String getUrl() {

        if (sUrl == null) {
            return String.format("%s:%s/apiv%s/", BuildConfig.WEB_SERVICE_URL, BuildConfig.WEB_SERVICE_PORT, BuildConfig.WEB_SERVICE_VERSION);

        }
        return sUrl;
    }
}
