package de.netalic.falcon.data.repository.receipt;

import java.util.List;

import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

public class ReceiptRepository implements IReceiptRepository {


    private static volatile ReceiptRepository sReceiptRepository;
    private ReceiptRestRepository mReceiptRestRepository;

    private ReceiptRepository() {

        mReceiptRestRepository = new ReceiptRestRepository();
    }

    public static ReceiptRepository getInstance() {

        if (sReceiptRepository== null) {

            synchronized (WalletRepository.class) {
                if (sReceiptRepository == null) {
                    sReceiptRepository = new ReceiptRepository();
                }
            }
        }
        return sReceiptRepository;
    }



    @Override
    public void update(Receipt receipt, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void get(Integer identifier, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository) {

    }

    @Override
    public void transfer(int sourceAddress, int walletId, double amount, CallRepository<Receipt> callRepository) {

        mReceiptRestRepository.transfer(sourceAddress,walletId,amount,callRepository);
    }
}
