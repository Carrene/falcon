package de.netalic.falcon.data.repository.receipt;

import java.util.List;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.repository.base.Deal;
import io.realm.Realm;

public class ReceiptRealmRepository implements IReceiptRepository {

    private Realm mRealm;

    @Override
    public void transfer(int sourceAddress, int walletId, double amount, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void update(Receipt receipt, CallRepository<Receipt> callRepository) {

        mRealm = Realm.getInstance(MyApp.sInsensitiveRealmConfiguration.build());
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(receipt);
        mRealm.commitTransaction();
        mRealm.close();
        callRepository.onDone(new Deal<>(receipt, null, null));
    }

    @Override
    public void get(Integer identifier, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository) {

    }
}
