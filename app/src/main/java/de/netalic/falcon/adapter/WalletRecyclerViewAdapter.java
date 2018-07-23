package de.netalic.falcon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;

public class WalletRecyclerViewAdapter extends RecyclerView.Adapter<WalletRecyclerViewAdapter.Holder> {

    List<String> mWalletList;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallet, parent, false);
        Holder holder = new Holder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.mTextViewCurrency.setText("Hi");
    }

    public WalletRecyclerViewAdapter(List<String> walletList) {

        mWalletList = walletList;
    }

    @Override
    public int getItemCount() {

        return mWalletList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView mTextViewCurrency;

        public Holder(View itemView) {

            super(itemView);
            mTextViewCurrency = itemView.findViewById(R.id.textView3);
        }
    }
}
