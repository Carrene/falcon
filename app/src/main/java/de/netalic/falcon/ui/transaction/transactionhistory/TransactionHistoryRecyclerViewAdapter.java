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
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.util.DateUtil;

public class TransactionHistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Receipt> mReceiptList = new ArrayList<>();

    public TransactionHistoryRecyclerViewAdapter() {

    }

    public void setDataSource(List<Receipt> depositList) {

        int size = mReceiptList.size();
        mReceiptList.addAll(depositList);
        notifyItemRangeInserted(size, depositList.size());
    }

    public void removeDataSource() {

        mReceiptList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transactionhistory, parent, false);
        return new ReceiptViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (mReceiptList != null && !mReceiptList.isEmpty()) {

            ReceiptViewHolder receiptViewHolder = (ReceiptViewHolder) holder;
            Receipt receipt = mReceiptList.get(position);

            receiptViewHolder.mTextViewWalletName.setText(receipt.getRecipientWalletName());
            receiptViewHolder.mTextViewAmount.setText(receipt.getQuoteCurrencySymbol() + " " + String.valueOf(receipt.getQouteAmount()));
            receiptViewHolder.mTextViewDateAndTime.setText(DateUtil.isoToDate(receipt.getCreatedAt()) + "@" + DateUtil.isoToTime(receipt.getCreatedAt()));
            receiptViewHolder.mTextViewTransactionType.setText(receipt.getType());
            receiptViewHolder.mTextViewTransactionResult.setText(receipt.getStatus());

            switch (receipt.getStatus()) {

                case "succeed": {
                    receiptViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#009688"));
                    break;
                }

                case "failed": {
                    receiptViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#DC3545"));
                    break;
                }

                default: {
                    receiptViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#000000"));

                }
            }
        }
    }

    @Override
    public int getItemCount() {

        return mReceiptList.size();
    }

    private class ReceiptViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewWalletName;
        private TextView mTextViewAmount;
        private TextView mTextViewDateAndTime;
        private TextView mTextViewTransactionResult;
        private TextView mTextViewTransactionType;


        private ReceiptViewHolder(View itemView) {

            super(itemView);
            mTextViewWalletName = itemView.findViewById(R.id.textview_transactionhistory_walletname);
            mTextViewAmount = itemView.findViewById(R.id.textview_transactionhistory_amount);
            mTextViewDateAndTime = itemView.findViewById(R.id.textview_transactionhistory_dateandtime);
            mTextViewTransactionResult = itemView.findViewById(R.id.textview_transactionhistory_transactionresult);
            mTextViewTransactionType = itemView.findViewById(R.id.textview_transactionhistory_type);

        }
    }
}