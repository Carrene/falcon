package de.netalic.falcon.data.repository.receipt;

import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.repository.base.IRepository;

public interface IReceiptRepository extends IRepository<Receipt,Integer> {

    void transfer(int sourceAddress,int walletId, double amount, CallRepository<Receipt> callRepository);
}
