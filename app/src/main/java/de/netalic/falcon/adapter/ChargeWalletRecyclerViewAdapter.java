package de.netalic.falcon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Wallet;

public class ChargeWalletRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Wallet> mWalletList;
    private int position;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {

            case 0: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_row_addwallet, parent, false);
                return new CreateHolder(itemView);
            }

            case 1: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_row_wallet, parent, false);
                return new Holder(itemView);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (position) {
            case 0: {
                break;
            }

            default: {
                if (this.position == position) {
                    ((Holder) holder).itemView.setAlpha(1);
                    ((Holder) holder).mImageViewCheck.setVisibility(View.VISIBLE);

                } else {
                    holder.itemView.setAlpha(0.5f);
                    ((Holder) holder).mImageViewCheck.setVisibility(View.GONE);
                }

                if (position == 0) {
                    ((Holder) holder).mRelativeLayout.setBackgroundResource(R.drawable.charge_wallet_dashedborder);
                }

                ((Holder) holder).mTextViewWalletName.setText(mWalletList.get(position).getName());
                ((Holder) holder).mTextViewWalletBalance.setText("" + mWalletList.get(position).getBalance());
            }
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull CreateHolder holder, int position) {
//
//        if (this.position == position) {
//            holder.itemView.setAlpha(1);
//            holder.mImageViewCheck.setVisibility(View.VISIBLE);
//
//        } else {
//            holder.itemView.setAlpha(0.5f);
//            holder.mImageViewCheck.setVisibility(View.GONE);
//        }
//
//        if (position == 0) {
//            holder.mRelativeLayout.setBackgroundResource(R.drawable.charge_wallet_dashedborder);
//        }
//
//        holder.mTextViewWalletName.setText(mWalletList.get(position).getName());
//        holder.mTextViewWalletBalance.setText("" + mWalletList.get(position).getBalance());
//
//    }


    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0: {
                return 0;
            }
            default: {
                return 1;
            }
        }
    }

    public ChargeWalletRecyclerViewAdapter(List<Wallet> walletList) {

        mWalletList = walletList;
    }

    public void select(int position) {

        this.position = position;
        notifyDataSetChanged();
    }

    public void setDataSource(List<Wallet> walletList) {

        mWalletList = walletList;
    }

    @Override
    public int getItemCount() {

        return mWalletList.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private TextView mTextViewWalletBalance;
        private TextView mTextViewWalletName;
        private ImageView mImageViewCheck;
        private RelativeLayout mRelativeLayout;

        private Holder(View itemView) {

            super(itemView);
            mRelativeLayout = itemView.findViewById(R.id.relativelayout_charge_wallet);
            mTextViewWalletBalance = itemView.findViewById(R.id.textView_charge_walletbalance);
            mTextViewWalletName = itemView.findViewById(R.id.textview_charge_walletname);
            mImageViewCheck = itemView.findViewById(R.id.imageview_charge_check);
        }
    }

    public static class CreateHolder extends RecyclerView.ViewHolder {

        private CreateHolder(View itemView) {

            super(itemView);

        }
    }
}
