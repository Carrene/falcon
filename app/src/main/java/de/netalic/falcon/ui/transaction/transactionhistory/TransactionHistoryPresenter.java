package de.netalic.falcon.ui.transaction.transactionhistory;

import com.google.common.base.Joiner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.deposit.DepositRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionHistoryPresenter implements TransactionHistoryContract.Presenter {

    private TransactionHistoryContract.View mTransactionHistoryView;
    private int mPaginationTake = 10;
    private int mPaginationSkip = 0;

    public TransactionHistoryPresenter(TransactionHistoryContract.View transactionHistoryView) {

        mTransactionHistoryView = checkNotNull(transactionHistoryView);
        mTransactionHistoryView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public synchronized void getDepositList(Map<String, ?> filterMap) {

        mTransactionHistoryView.showPaginationLoading(true);
        mTransactionHistoryView.showPaginationError(false);

        Map<String, String> queryString = createQueryString(filterMap);

        RepositoryLocator.getInstance().getRepository(DepositRepository.class).getAll(deal -> {
            if (deal.getThrowable() != null) {
                mTransactionHistoryView.showPaginationError(true);
                mTransactionHistoryView.showPaginationLoading(false);
            } else {
                switch (deal.getResponse().code()) {

                    case 200: {

                        mTransactionHistoryView.showPaginationLoading(false);
                        mTransactionHistoryView.showPaginationError(false);
                        mPaginationTake = Integer.parseInt(deal.getResponse().headers().get("X-Pagination-Take"));
                        mPaginationSkip = Integer.parseInt(deal.getResponse().headers().get("X-Pagination-Skip"));
                        int paginationCount = Integer.parseInt(deal.getResponse().headers().get("X-Pagination-Count"));

                        mTransactionHistoryView.setDepositList(deal.getResponse().body());
                        if (mPaginationSkip >= paginationCount) {
                            mTransactionHistoryView.loadNoMoreItem(true);
                            mPaginationSkip = 0;
                        }

                        mPaginationSkip += mPaginationTake;
                        break;
                    }

                    default: {
                        mTransactionHistoryView.showPaginationError(true);
                    }
                }
            }
        }, queryString, mPaginationTake, mPaginationSkip);
    }

    @Override
    public void resetPagination() {

        mPaginationSkip = 0;
        mTransactionHistoryView.loadNoMoreItem(false);
    }

    private Map<String, String> createQueryString(Map<String, ?> filterMap) {

        Map<String, String> queryString = new HashMap<>();
        Map<String, Set<String>> queryStringMap = new HashMap<>();

        for (Map.Entry<String, ?> entry : filterMap.entrySet()) {
            if (entry.getValue().toString().equals("true")) {
                if (entry.getKey().equals("succeed") || entry.getKey().equals("failed")) {
                    if (queryStringMap.get("status") == null) {
                        queryStringMap.put("status", new HashSet<>());
                    }
                    queryStringMap.get("status").add(entry.getKey());
                }
            }
        }

        for (String key : queryStringMap.keySet()) {
            queryString.put(key, "IN(" + Joiner.on(",").join(queryStringMap.get("status").iterator()) + ")");
        }

        return queryString;
    }
}