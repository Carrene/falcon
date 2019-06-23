package de.netalic.falcon.data.repository.receipt;

import android.content.Context;
import android.os.AsyncTask;
import java.util.List;
import java.util.Map;
import de.netalic.falcon.SensitiveDatabase;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.data.repository.base.Deal;

public class ReceiptRoomRepository implements IReceiptRepository {

    private SensitiveDatabase mSensitiveDatabase;
    private Context mContext;


    public ReceiptRoomRepository(Context context) {
        mContext = context;
    }

    @Override
    public void transfer(int sourceAddress, int walletId, double amount, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository, Map<String, String> query, int take, int skip) {

    }

    @Override
    public void update(Receipt receipt, CallRepository<Receipt> callRepository) {

        AsyncTask.execute(() -> {

            mSensitiveDatabase=SensitiveDatabase.getSensitiveDatabase(mContext);
            mSensitiveDatabase.receiptDao().insertReceipt(receipt);
            mSensitiveDatabase.close();
            callRepository.onDone(new Deal<>(receipt,null,null));
        });

    }

    @Override
    public void get(Integer identifier, CallRepository<Receipt> callRepository) {

    }

    @Override
    public void getAll(CallRepository<List<Receipt>> callRepository) {

    }
}
