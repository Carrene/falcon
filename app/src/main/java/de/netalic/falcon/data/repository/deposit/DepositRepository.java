package de.netalic.falcon.data.repository.deposit;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Deposit;

public class DepositRepository implements IDepositRepository {

    private IDepositRepository mDepositRestRepository;
    private IDepositRepository mDepositRealmRepository;

    private DepositRepository() {

    }

    public DepositRepository(IDepositRepository restRepository, IDepositRepository realmRepostiory) {

        mDepositRestRepository = restRepository;
        mDepositRealmRepository = realmRepostiory;
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
    public void getAll(CallRepository<List<Deposit>> callRepository, Map<String, String> options, int take, int skip) {

        mDepositRestRepository.getAll(callRepository, options, take, skip);
    }
}
