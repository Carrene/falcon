package de.netalic.falcon.ui.transaction.transactionhistory;

import com.google.common.base.Joiner;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.receipt.ReceiptRepository;
import de.netalic.falcon.util.DateUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class TransactionHistoryPresenter implements TransactionHistoryContract.Presenter {

    private TransactionHistoryContract.View mTransactionHistoryView;
    private int mPaginationTake = 25;
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

        RepositoryLocator.getInstance().getRepository(ReceiptRepository.class).getAll(deal -> {

            mTransactionHistoryView.showProgressBar();
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
                        mPaginationSkip += mPaginationTake;

                        if (mPaginationSkip >= paginationCount) {
                            mTransactionHistoryView.loadNoMoreItem(true);
                            mPaginationSkip = 0;
                        }
                        break;
                    }

                    default: {
                        mTransactionHistoryView.showPaginationError(true);
                    }
                }
            }

            mTransactionHistoryView.dismissProgressBar();
        }, queryString, mPaginationTake, mPaginationSkip);
    }

    @Override
    public void resetPagination() {

        mPaginationSkip = 0;
        mTransactionHistoryView.loadNoMoreItem(false);
    }

    private Map<String, String> createQueryString(Map<String, ?> filterMap) {
        //TODO(Ehsan) Complete time filtering
        Map<String, String> queryString = new HashMap<>();
        Map<String, Set<String>> queryStringMap = new HashMap<>();

        for (Map.Entry<String, ?> entry : filterMap.entrySet()) {
            if (entry.getValue().toString().equals("true")) {
                if (entry.getKey().equals("succeed") || entry.getKey().equals("failed")) {
                    if (queryStringMap.get("status") == null) {
                        queryStringMap.put("status", new HashSet<>());
                    }
                    queryStringMap.get("status").add(entry.getKey());
                } else if (entry.getKey().equals("withdraw") || entry.getKey().equals("charge") || entry.getKey().equals("transfer") || entry.getKey().equals("purchase")) {
                    if (queryStringMap.get("type") == null) {
                        queryStringMap.put("type", new HashSet<>());
                    }
                    queryStringMap.get("type").add(entry.getKey());
                }
            } else if (entry.getKey().equals("date") && entry.getValue() != null && !entry.getValue().equals("Always")) {
                if (queryStringMap.get("createdAt") == null) {
                    queryStringMap.put("createdAt", new HashSet<>());
                }
                switch (entry.getValue().toString()) {
                    case "Last day": {
                        LinkedHashSet linkedHashSet = new LinkedHashSet();
                        linkedHashSet.add(DateUtil.lastDayToIso());
                        linkedHashSet.add(DateUtil.nowToIso());
                        queryStringMap.put("createdAt", linkedHashSet);
                        break;
                    }
                    case "Last week": {
                        LinkedHashSet linkedHashSet = new LinkedHashSet();
                        linkedHashSet.add(DateUtil.lastWeekToIso());
                        linkedHashSet.add(DateUtil.nowToIso());
                        queryStringMap.put("createdAt", linkedHashSet);
                        break;
                    }

                    case "Last month": {
                        LinkedHashSet linkedHashSet = new LinkedHashSet();
                        linkedHashSet.add(DateUtil.lastMonthToIso());
                        linkedHashSet.add(DateUtil.nowToIso());
                        queryStringMap.put("createdAt", linkedHashSet);
                        break;
                    }
                }
            }
        }
        for (String key : queryStringMap.keySet()) {
            if (key.equals("createdAt")) {
                queryString.put(key, "BETWEEN(" + Joiner.on(",").join(queryStringMap.get(key).iterator()) + ")");
            } else {
                queryString.put(key, "IN(" + Joiner.on(",").join(queryStringMap.get(key).iterator()) + ")");
            }
        }
        return queryString;
    }
}