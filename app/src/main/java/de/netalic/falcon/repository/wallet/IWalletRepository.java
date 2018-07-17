package de.netalic.falcon.repository.wallet;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.repository.IRepository;

public interface IWalletRepository extends IRepository<Wallet,Integer> {

    void getToken(int id, double amount, CallRepository<String>callRepository);

}
