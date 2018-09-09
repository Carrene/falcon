package de.netalic.falcon.data.repository.wallet;

import de.netalic.falcon.data.model.Deposit;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.data.repository.base.IRepository;

public interface IWalletRepository extends IRepository<Wallet, Integer> {

    void charge(int id, double amount, CallRepository<Deposit> callRepository);

    void finalize(int walletId, int depositId,String braintreeNonce, CallRepository<Deposit> callRepository);
}
