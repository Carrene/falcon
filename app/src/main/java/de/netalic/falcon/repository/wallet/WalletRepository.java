package de.netalic.falcon.repository.wallet;

import java.util.List;

import de.netalic.falcon.model.Wallet;

public class WalletRepository implements IWallet {

    private static volatile WalletRepository sWalletRepository;
    private WalletRestRepository mWalletRestRepository;

    private WalletRepository(){

        mWalletRestRepository=new WalletRestRepository();
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

    }

    @Override
    public void get(Integer identifier, CallRepository<Wallet> callRepository) {

    }

    @Override
    public void getList(CallRepository<List<Wallet>> callRepository) {

        mWalletRestRepository.getList(callRepository);
    }
}
