package de.netalic.falcon.repository.wallet;

import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.repository.IRepository;

public interface IWalletRepository extends IRepository<Wallet, Integer> {

    void charge(int id, double amount, CallRepository<Deposit> callRepository);

    void finalize(int walletId, int depositId,String braintreeNonce, CallRepository<Deposit> callRepository);
}
