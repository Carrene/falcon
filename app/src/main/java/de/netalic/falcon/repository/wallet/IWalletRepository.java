package de.netalic.falcon.repository.wallet;

import com.google.gson.JsonObject;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.repository.IRepository;

public interface IWalletRepository extends IRepository<Wallet,Integer> {

    void getToken(int id, double amount, CallRepository<JsonObject>callRepository);

}
