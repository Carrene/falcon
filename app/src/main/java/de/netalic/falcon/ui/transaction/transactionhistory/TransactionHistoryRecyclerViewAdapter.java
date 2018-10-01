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
        return new DepositViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (mReceiptList != null && !mReceiptList.isEmpty()) {

            DepositViewHolder depositViewHolder = (DepositViewHolder) holder;
            Receipt receipt = mReceiptList.get(position);

//            depositViewHolder.mTextViewWalletName.setText(receipt.getWalletName());
//            depositViewHolder.mTextViewAmount.setText(receipt.getPaymentGatewayCurrencySymbol() + " " + String.valueOf(receipt.getPaidAmount()));
//            depositViewHolder.mTextViewDateAndTime.setText(DateUtil.isoToDate(receipt.getCreatedAt()) + "@" + DateUtil.isoToTime(receipt.getCreatedAt()));
//            depositViewHolder.mTextViewTransactionResult.setText(receipt.getStatus());

//            switch (receipt.getStatus()) {
//
//                case "succeed": {
//                    depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#009688"));
//                    break;
//                }
//
//                case "failed": {
//                    depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#DC3545"));
//                    break;
//                }
//            }
        }
    }

    @Override
    public int getItemCount() {

        return mReceiptList.size();
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