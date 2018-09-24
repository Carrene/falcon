package de.netalic.falcon.data.repository.transaction;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.repository.base.IRepository;

public interface ITransactionRepository extends IRepository<Transaction,Integer> {

    void startTransfer(int sourceWalletId,String destinationWalletId,float transferAmount,CallRepository<Transaction>callRepository);
}
