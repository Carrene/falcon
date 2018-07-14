package de.netalic.falcon.repository.wallet;

import java.util.List;

import de.netalic.falcon.model.Wallet;
import de.netalic.falcon.repository.IRepository;

public interface IWallet extends IRepository<Wallet,Integer> {

    void getList(CallRepository<List<Wallet>>callRepository);
}
