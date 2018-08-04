package de.netalic.falcon.network;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import de.netalic.falcon.BuildConfig;
import de.netalic.falcon.MyApp;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit sRetrofit = null;
    private static ApiInterface sApi;

    private static Retrofit getClient() {

        if (sRetrofit == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
            okHttpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
            ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.getInstance()));
            okHttpClient.cookieJar(cookieJar).addInterceptor(new AuthorizationInterceptor());
            okHttpClient.addInterceptor(new NetworkErrorInterceptor());
            sRetrofit = new Retrofit.Builder().baseUrl(getUrl())
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static Retrofit getMockUpClient(String url) {

        if (sRetrofit == null) {

            OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
            okHttpClient.readTimeout(1, TimeUnit.MINUTES).connectTimeout(1, TimeUnit.MINUTES);
            okHttpClient.addInterceptor(new AuthorizationInterceptor());
            okHttpClient.addInterceptor(new NetworkErrorInterceptor());
            sRetrofit = new Retrofit.Builder().baseUrl(url)
                    .client(okHttpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return sRetrofit;
    }

    public static ApiInterface getService() {

        if (sApi == null) {
            sApi = getClient().create(ApiInterface.class);
        }
        return sApi;
    }

    public static ApiInterface setService(Retrofit retrofit) {

        if (sApi == null) {
            sApi = retrofit.create(ApiInterface.class);
        }
        return sApi;
    }

    public static String getUrl() {

        String url = BuildConfig.WEB_SERVICE_URL + ":" + BuildConfig.WEB_SERVICE_PORT + "/apiv1/";
        return url;

    }

    private static class NetworkErrorInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Response response = null;
            try {
                Request request = chain.request();
                response = chain.proceed(request);
                if (response.code() == 500) {
                    //TODO:(Milad) Display error
                    System.out.println("ERROR");
                }
            } catch (SocketTimeoutException socketTimeoutException) {
                //TODO (Milad) Display error
            }
            return response;
        }
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

            if (response.request().method().equals("BIND") && response.code() == 200) {

                JsonParser jsonParser = new JsonParser();
                String responseBodyString = response.body().string();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(responseBodyString);
                String freshToken = jsonObject.get("token").getAsString();
                sharedPreferencesJwtPersistor.save(freshToken);
                MediaType contentType = response.body().contentType();
                ResponseBody body = ResponseBody.create(contentType, responseBodyString);
                response = response.newBuilder().body(body).build();
            }

            String newJwtToken = response.header("X-New-JWT-Token");

            if (newJwtToken != null) {
                sharedPreferencesJwtPersistor.save(newJwtToken);
            }

            return response;
        }
    }
}