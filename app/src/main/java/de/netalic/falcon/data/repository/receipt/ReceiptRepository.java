package de.netalic.falcon.data.repository.receipt;

import java.util.List;

import de.netalic.falcon.data.model.Receipt;

public class ReceiptRepository implements IReceiptRepository {


    private static volatile ReceiptRepository sReceiptRepository;
    private IReceiptRepository mReceiptRestRepository;
    private IReceiptRepository mReceiptRealmRepository;

    private ReceiptRepository() {

        mReceiptRestRepository = new ReceiptRestRepository();
    }

    public ReceiptRepository(IReceiptRepository restRepository, IReceiptRepository realmRepository) {

        mReceiptRestRepository = restRepository;
        mReceiptRealmRepository = realmRepository;
    }

    public static ReceiptRepository getInstance() {

        if (sReceiptRepository == null) {

            synchronized (ReceiptRepository.class) {
                if (sReceiptRepository == null) {
                    sReceiptRepository = new ReceiptRepository();
                }
            }
        }
        return sReceiptRepository;
    }


    @Override
    public void update(Receipt receipt, CallRepository<Receipt> callRepository) {

        mReceiptRealmRepository.update(receipt, callRepository);
    }

    @Override
    public void get(Integer identifier, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository) {

        mReceiptRestRepository.getAll(callRepository);
    }

    @Override
    public void transfer(int sourceAddress, int walletId, double amount, CallRepository<Receipt> callRepository) {

        mReceiptRestRepository.transfer(sourceAddress, walletId, amount, callRepository);
    }
}
