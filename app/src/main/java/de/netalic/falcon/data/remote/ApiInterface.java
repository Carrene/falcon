package de.netalic.falcon.data.remote;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.model.Rate;
import de.netalic.falcon.model.User;
import de.netalic.falcon.model.Wallet;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Call<Rate> exchangeRate(@Path("currency") String currency);

    @HTTP(method = "LIST", path = "rates")
    Call<List<Rate>> listExchangeRate();

    @HTTP(method = "LIST", path = "wallets")
    Call<List<Wallet>> walletList();

    @FormUrlEncoded
    @HTTP(method = "START", path = "wallets/{id}/braintree/deposits", hasBody = true)
    Call<Deposit> charge(@Path("id") int id, @Field("amount") double amount);

    @FormUrlEncoded
    @HTTP(method = "FINALIZE", path = "wallets/{walletId}/braintree/deposits/{depositId}", hasBody = true)
    Call<Deposit> finalize(@Path("walletId") int walletId, @Path("depositId") int depositId, @Field("braintreeNonce") String braintreeNonce);

    @HTTP(method = "LIST", path = "deposits")
    Call<List<Deposit>> depositList(@QueryMap(encoded = true) Map<String, String> options, @Query("take") int take,  @Query("skip") int skip);

}