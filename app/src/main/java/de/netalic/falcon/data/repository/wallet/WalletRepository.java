package de.netalic.falcon.data.repository.wallet;

import java.util.List;

import de.netalic.falcon.data.model.Deposit;
import de.netalic.falcon.data.model.Wallet;

public class WalletRepository implements IWalletRepository {

    private IWalletRepository mWalletRestRepository;
    private IWalletRepository mWalletRealmRepository;

    private WalletRepository() {

    }

    public WalletRepository(IWalletRepository restRepository, IWalletRepository realmRepository) {

        mWalletRestRepository = restRepository;
        mWalletRealmRepository = realmRepository;
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
    public void charge(int id, double amount, CallRepository<Deposit> callRepository) {

        mWalletRestRepository.charge(id, amount, callRepository);
    }

    @Override
    public void finalize(int walletId, int depositId, String braintreeNonce, CallRepository<Deposit> callRepository) {

        mWalletRestRepository.finalize(walletId, depositId, braintreeNonce, callRepository);
    }


}
