package de.netalic.falcon.ui.transaction.transactionhistory;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Deposit;
import de.netalic.falcon.util.DateUtil;

public class TransactionHistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Deposit> mDepositList = new ArrayList<>();

    public TransactionHistoryRecyclerViewAdapter() {

    }

    public void setDataSource(List<Deposit> depositList) {

        int size = mDepositList.size();
        mDepositList.addAll(depositList);
        notifyItemRangeInserted(size, depositList.size());
    }

    public void removeDataSource() {

        mDepositList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transactionhistory, parent, false);
        return new DepositViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (mDepositList != null && !mDepositList.isEmpty()) {

            DepositViewHolder depositViewHolder = (DepositViewHolder) holder;
            Deposit deposit = mDepositList.get(position);

            depositViewHolder.mTextViewWalletName.setText(deposit.getWalletName());
            depositViewHolder.mTextViewAmount.setText(String.valueOf(deposit.getPaidAmount()));
            depositViewHolder.mTextViewDateAndTime.setText(DateUtil.isoToDate(deposit.getCreatedAt()) + "@" + DateUtil.isoToTime(deposit.getCreatedAt()));
            depositViewHolder.mTextViewTransactionResult.setText(deposit.getStatus());

            switch (deposit.getStatus()) {

                case "succeed": {
                    depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#009688"));
                    break;
                }

                case "failed": {
                    depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#DC3545"));
                    break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {

        return mDepositList.size();
    }

    private class DepositViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewWalletName;
        private TextView mTextViewAmount;
        private TextView mTextViewDateAndTime;
        private TextView mTextViewTransactionResult;

        private DepositViewHolder(View itemView) {

            super(itemView);
            mTextViewWalletName = itemView.findViewById(R.id.textview_transactionhistory_walletname);
            mTextViewAmount = itemView.findViewById(R.id.textview_transactionhistory_amount);
            mTextViewDateAndTime = itemView.findViewById(R.id.textview_transactionhistory_dateandtime);
            mTextViewTransactionResult = itemView.findViewById(R.id.textview_transactionhistory_transactionresult);

        }
    }
}