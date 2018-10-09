package de.netalic.falcon.data.remote;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Currency;
import de.netalic.falcon.data.model.Rate;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.model.Wallet;
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
    Call<Rate> getRate(@Path("currency") String currency);

    @HTTP(method = "LIST", path = "rates")
    Call<List<Rate>> listRate();

    @HTTP(method = "LIST", path = "wallets")
    Call<List<Wallet>> walletList();

    @FormUrlEncoded
    @HTTP(method = "START", path = "wallets/{id}/braintree/charges", hasBody = true)
    Call<Transaction> charge(@Path("id") int id, @Field("amount") double amount, @Field("verifyRateId") int verifyRateId);

    @FormUrlEncoded
    @HTTP(method = "FINALIZE", path = "braintree/charges/{transaction_id}", hasBody = true)
    Call<Transaction> finalize(@Path("transaction_id") int transaction_id, @Field("braintreeNonce") String braintreeNonce);

    @GET("receipts/{id}")
    Call<Receipt> getReceipt(@Path("id") int id);

    @HTTP(method = "LIST", path = "receipts?sort=createdAt")
    Call<List<Receipt>> receiptList();

    @HTTP(method = "LIST", path = "receipts?sort=-createdAt")
    Call<List<Receipt>> receiptList(@QueryMap(encoded = true) Map<String, String> options, @Query("take") int take, @Query("skip") int skip);

    @FormUrlEncoded
    @HTTP(method = "START", path = "wallets/{wallet_id}/transfers", hasBody = true)
    Call<Transaction> startTransfer(@Path("wallet_id") int sourceWalletAddress, @Field("destinationWalletAddress") String destinationWalletAddress, @Field("amount") float amount);

    @HTTP(method = "FINALIZE", path = "transfers/{transaction_id}")
    Call<Transaction> finalizeTransfer(@Path("transaction_id") int transactionId);

    @HTTP(method ="LIST" ,path ="currencies")
    Call<List<Currency>>currencyList();

    @HTTP(method = "UPDATE",path = "clients/{id}/basecurrencies")
    Call<User>changeBaseCurrency(@Path("id") int id,@Field("baseCurrencyCode") String baseCurrencyCode);
}

