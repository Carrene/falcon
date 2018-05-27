package de.netalic.falcon.network;

import de.netalic.falcon.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;

public interface ApiInterface {


    @FormUrlEncoded
    @HTTP(method = "CLAIM", path = "devices", hasBody = true)
    Call<User> claimDevice(@Field("udid") String udid, @Field("phone") String phone);

}