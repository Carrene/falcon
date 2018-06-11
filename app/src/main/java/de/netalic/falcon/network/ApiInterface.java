package de.netalic.falcon.network;

import de.netalic.falcon.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;

public interface ApiInterface {


    @FormUrlEncoded
    @HTTP(method = "CLAIM", path = "phones", hasBody = true)
    Call<User> claim(@Field("udid") String udid, @Field("phone") String phone);

    @FormUrlEncoded
    @HTTP(method = "BIND", path = "phones", hasBody = true)
    Call<User> bind(@Field("udid") String udid, @Field("phone") String phone, @Field("deviceName") String deviceName, @Field("activationCode") String activationCode);

    @FormUrlEncoded
    @HTTP(method = "SET", path = "emails", hasBody = true)
    Call<User> setEmail(String email);
}