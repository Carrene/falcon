package de.netalic.falcon.data.repository.transaction;

import java.util.List;

import de.netalic.falcon.data.model.Transaction;

public class TransactionRepository implements ITransactionRepository {


    private ITransactionRepository mTransactionRestRepository;
    private ITransactionRepository mTransactionRealmRepository;

    private TransactionRepository() {

    }

    public TransactionRepository(ITransactionRepository restRepository, ITransactionRepository realmRepository) {

        mTransactionRestRepository = restRepository;
        mTransactionRealmRepository = realmRepository;
    }


    @Override
    public void update(Transaction transaction, CallRepository<Transaction> callRepository) {

        mTransactionRestRepository.update(transaction, callRepository);

    }

    @Override
    public void get(Integer identifier, CallRepository<Transaction> callRepository) {

        mTransactionRestRepository.get(identifier, callRepository);
    }

    @Override
    public void getAll(CallRepository<List<Transaction>> callRepository) {

        mTransactionRestRepository.getAll(callRepository);
    }

    @Override
    public void startTransfer(int sourceWalletId, String destinationWalletId, float transferAmount, CallRepository<Transaction> callRepository) {

        mTransactionRestRepository.startTransfer(sourceWalletId, destinationWalletId, transferAmount, callRepository);
    }
}
