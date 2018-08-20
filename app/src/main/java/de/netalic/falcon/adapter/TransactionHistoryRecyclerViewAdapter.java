package de.netalic.falcon.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.util.DateUtil;

public class TransactionHistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Deposit> mDepositList;


    public TransactionHistoryRecyclerViewAdapter(List<Deposit> depositList) {

        mDepositList = depositList;
    }

    public void setDataSource(List<Deposit> depositList) {

        mDepositList = depositList;
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

            switch (deposit.getStatus()) {

                case "succeed": {
                    depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#009688"));
                    depositViewHolder.mTextViewDateAndTime.setText(DateUtil.isoToDate(deposit.getModifiedAt()) + "@" + DateUtil.isoToTime(deposit.getModifiedAt()));
                    depositViewHolder.mTextViewTransactionResult.setText(deposit.getStatus());
                    break;
                }

                case "failed": {
                    depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#DC3545"));
                    depositViewHolder.mTextViewDateAndTime.setText(DateUtil.isoToDate(deposit.getModifiedAt()) + "@" + DateUtil.isoToTime(deposit.getModifiedAt()));
                    depositViewHolder.mTextViewTransactionResult.setText(deposit.getStatus());
                    break;
                }

                case "new": {
                    depositViewHolder.mTextViewTransactionResult.setText(deposit.getStatus());
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
