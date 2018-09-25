package de.netalic.falcon.data.repository.transfer;

import java.util.List;

import de.netalic.falcon.data.model.Transaction;

public class TransferRepository implements ITransferRepository {


    private ITransferRepository mTransactionRestRepository;
    private ITransferRepository mTransactionRealmRepository;

    private TransferRepository() {

    }

    public TransferRepository(ITransferRepository restRepository, ITransferRepository realmRepository) {

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
