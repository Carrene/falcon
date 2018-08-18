package de.netalic.falcon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;

public class TransactionHistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Deposit> mDepositList;


    public TransactionHistoryRecyclerViewAdapter(List<Deposit> depositList, Context context) {

        mContext = context;
        mDepositList = depositList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transactionhistory, parent, false);
        return new DepositViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DepositViewHolder depositViewHolder = (DepositViewHolder) holder;
        Deposit deposit = mDepositList.get(position);
        depositViewHolder.mTextViewWalletName.setText(deposit.getWalletName());
        depositViewHolder.mTextViewAmount.setText(String.valueOf(deposit.getPaidAmount()));

        try {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
            Date baseDate = dateFormat.parse(deposit.getModifiedAt());
            DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat time = new SimpleDateFormat("h:mm a");

            depositViewHolder.mTextViewDateAndTime.setText(date.format(baseDate) + "@" + time.format(baseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (deposit.getStatus().equals(mContext.getString(R.string.transactionhistoryrecyclerviewadapter_succeed))) {

            depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#009688"));
            depositViewHolder.mTextViewTransactionResult.setText(deposit.getStatus());
        }
        if (deposit.getStatus().equals(mContext.getString(R.string.transactionhistoryrecyclerviewadapter_failed))) {

            depositViewHolder.mTextViewTransactionResult.setTextColor(Color.parseColor("#DC3545"));
            depositViewHolder.mTextViewTransactionResult.setText(deposit.getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return mDepositList.size();
    }

    public class DepositViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewWalletName;
        public TextView mTextViewAmount;
        public TextView mTextViewDateAndTime;
        public TextView mTextViewTransactionResult;

        public DepositViewHolder(View itemView) {
            super(itemView);
            mTextViewWalletName = itemView.findViewById(R.id.textview_transactionhistory_walletname);
            mTextViewAmount = itemView.findViewById(R.id.textview_transactionhistory_amount);
            mTextViewDateAndTime = itemView.findViewById(R.id.textview_transactionhistory_dateandtime);
            mTextViewTransactionResult = itemView.findViewById(R.id.textview_transactionhistory_transactionresult);

        }
    }
}
