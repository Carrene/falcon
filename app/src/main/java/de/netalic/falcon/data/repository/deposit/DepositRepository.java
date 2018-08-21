package de.netalic.falcon.data.repository.deposit;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.repository.base.IRepository;
import de.netalic.falcon.data.repository.wallet.WalletRepository;
import de.netalic.falcon.model.Deposit;

public class DepositRepository implements IDepositRepository {

    private static volatile DepositRepository sDepositRepository;
    private DepositRestRepository mDepositRestRepository;

    private DepositRepository() {

        mDepositRestRepository = new DepositRestRepository();
    }

    public static DepositRepository getInstance() {

        if (sDepositRepository == null) {

            synchronized (WalletRepository.class) {

                sDepositRepository = new DepositRepository();
            }
        }

        return sDepositRepository;
    }

    @Override
    public void update(Deposit deposit, CallRepository<Deposit> callRepository) {

        mDepositRestRepository.update(deposit, callRepository);
    }

    @Override
    public void get(Integer identifier, CallRepository<Deposit> callRepository) {

        mDepositRestRepository.get(identifier, callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Deposit>> callRepository) {

        mDepositRestRepository.getAll(callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Deposit>> callRepository, Map<String, String> options) {

        mDepositRestRepository.getAll(callRepository, options);
    }
}
