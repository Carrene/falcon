package de.netalic.falcon.repository.wallet;

import java.util.List;

import de.netalic.falcon.model.Wallet;

public class WalletRepositoryRepository implements IWalletRepository {

    private static volatile WalletRepositoryRepository sWalletRepository;
    private WalletRepositoryRestRepository mWalletRestRepository;

    private WalletRepositoryRepository() {

        mWalletRestRepository = new WalletRepositoryRestRepository();
    }

    public static WalletRepositoryRepository getInstance() {

        if (sWalletRepository == null) {

            synchronized (WalletRepositoryRepository.class) {
                if (sWalletRepository == null) {
                    sWalletRepository = new WalletRepositoryRepository();
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

}
