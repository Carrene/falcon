package de.netalic.falcon.network;

import com.google.gson.JsonObject;

import java.util.List;

import de.netalic.falcon.model.ExchangeRate;
import de.netalic.falcon.model.User;
import de.netalic.falcon.model.Wallet;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;

public interface ApiInterface {


    @FormUrlEncoded
    @HTTP(method = "CLAIM", path = "phones", hasBody = true)
    Call<User> claim(@Field("udid") String udid, @Field("phone") String phone);

    @FormUrlEncoded
    @HTTP(method = "BIND", path = "phones", hasBody = true)
    Call<User> bind(@Field("udid") String udid, @Field("phone") String phone, @Field("deviceName") String deviceName, @Field("activationCode") String activationCode);

    @FormUrlEncoded
    @HTTP(method = "SET", path = "emails", hasBody = true)
    Call<User> setEmail(@Field("email") String email);

    @GET("rates/{currency}")
    Call<ExchangeRate> exchangeRate(@Path("currency") String currency);

    @HTTP(method = "LIST", path = "wallets")
    Call<List<Wallet>> walletList();

    @FormUrlEncoded
    @HTTP(method = "CHARGE", path = "wallets/{id}/braintree", hasBody = true)
    Call<JsonObject> charge(@Path("id") int id, @Field("amount") double amount);


}