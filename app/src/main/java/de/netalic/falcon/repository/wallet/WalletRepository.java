package de.netalic.falcon.repository.wallet;

import com.google.gson.JsonObject;

import java.util.List;

import de.netalic.falcon.model.Wallet;

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

        mWalletRestRepository.update(wallet,callRepository);
    }

    @Override
    public void get(Integer identifier, CallRepository<Wallet> callRepository) {

        mWalletRestRepository.get(identifier,callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Wallet>> callRepository) {

        mWalletRestRepository.getAll(callRepository);
    }

    @Override
    public void getToken(int id, double amount, CallRepository<JsonObject> callRepository) {

        mWalletRestRepository.getToken(id,amount,callRepository);
    }
}
