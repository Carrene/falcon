package de.netalic.falcon.repository.wallet;

import com.google.gson.JsonObject;

import java.util.List;

import de.netalic.falcon.model.ChargeStartResponse;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.repository.IRepository;

public class WalletRepository implements IWalletRepository {

    private static volatile WalletRepository sWalletRepository;
    private WalletRestRepository mWalletRestRepository;

    private WalletRepository() {

        mWalletRestRepository = new WalletRestRepository();
    }

    public static WalletRepository getInstance() {

        if (sWalletRepository == null) {

            synchronized (WalletRepository.class) {
                if (sWalletRepository == null) {
                    sWalletRepository = new WalletRepository();
                }
            }
        }
        return sWalletRepository;
    }

    @Override
    public void update(Wallet wallet, CallRepository<Wallet> callRepository) {

        mWalletRestRepository.update(wallet, callRepository);
    }

    @Override
    public void get(Integer identifier, CallRepository<Wallet> callRepository) {

        mWalletRestRepository.get(identifier, callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Wallet>> callRepository) {

        mWalletRestRepository.getAll(callRepository);
    }

    @Override
    public void charge(int id, double amount, CallRepository<ChargeStartResponse> callRepository) {

        mWalletRestRepository.charge(id, amount, callRepository);
    }

    @Override
    public void finalize(double amount, String braintreeNonce, String chargeDataToken, CallRepository<JsonObject> callRepository) {

        mWalletRestRepository.finalize(amount, braintreeNonce, chargeDataToken, callRepository);
    }
}
