package de.netalic.falcon.data.repository.receipt;

import java.util.List;
import java.util.Map;

import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.repository.base.IRepository;

public interface IReceiptRepository extends IRepository<Receipt, Integer> {

    void transfer(int sourceAddress, int walletId, double amount, CallRepository<Receipt> callRepository);

    void getAll(CallRepository<List<Receipt>> callRepository, Map<String, String> query, int take, int skip);

}
