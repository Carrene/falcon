package de.netalic.falcon.data.repository.transfer;

import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.data.repository.base.IRepository;

public interface ITransferRepository extends IRepository<Transaction,Integer> {

    void startTransfer(int sourceWalletId,String destinationWalletId,float transferAmount,CallRepository<Transaction>callRepository);
}
