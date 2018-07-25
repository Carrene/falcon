package de.netalic.falcon.repository.wallet;

import com.google.gson.JsonObject;

import de.netalic.falcon.model.ChargeStartResponse;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.repository.IRepository;

public interface IWalletRepository extends IRepository<Wallet, Integer> {

    void charge(int id, double amount, CallRepository<ChargeStartResponse> callRepository);

    void finalize(double amount, String braintreeNonce, String chargeDataToken, CallRepository<JsonObject> callRepository);
}
