package de.netalic.falcon.network;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import de.netalic.falcon.BuildConfig;
import de.netalic.falcon.MyApp;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit sRetrofit = null;
    private static ApiInterface sApi;
    public static String sTestUrl;

    private static Retrofit getClient() {


        if (sRetrofit == null) {

            if (sTestUrl == null) {
                OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
                okHttpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
                ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.getInstance()));
                okHttpClient.cookieJar(cookieJar).addInterceptor(new AuthorizationInterceptor());
                sRetrofit = new Retrofit.Builder().baseUrl(getUrl())
                        .client(okHttpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            } else {
                OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
                okHttpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
                okHttpClient.addInterceptor(new AuthorizationInterceptor());
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

    private static class AuthorizationInterceptor implements Interceptor {

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance());


        @Override
        public Response intercept(Chain chain) throws IOException {

            String token = sharedPreferencesJwtPersistor.get();
            Request request = chain.request();

            if (token != null) {
                request = request.newBuilder().addHeader("Authorization", "Bearer " + token).build();
            }

            Response response = chain.proceed(request);

            if (response.request().method().equals("BIND")) {

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(response.body().string());
                String freshToken = jsonObject.get("token").getAsString();
                sharedPreferencesJwtPersistor.save(freshToken);
            }
            String newJwtToken = response.header("X-New-JWT-Token");

            if (newJwtToken != null) {
                sharedPreferencesJwtPersistor.save(newJwtToken);
            }
            return response;
        }
    }
}