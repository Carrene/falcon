package de.netalic.falcon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Wallet;

public class ChargeWalletRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Wallet> mWalletList;
    private int mSelectedPosition;

    private static final int ADD_WALLET = 0;
    private static final int WALLET = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {

            case ADD_WALLET: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_row_addwallet, parent, false);
                return new AddWalletHolder(itemView);
            }

            case WALLET: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_row_wallet, parent, false);
                return new WalletHolder(itemView);
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

                if (mSelectedPosition == position) {
                    ((WalletHolder) holder).itemView.setAlpha(1);
                    ((WalletHolder) holder).mImageViewCheck.setVisibility(View.VISIBLE);
                } else {
                    holder.itemView.setAlpha(0.5f);
                    ((WalletHolder) holder).mImageViewCheck.setVisibility(View.GONE);
                }

                ((WalletHolder) holder).mTextViewWalletName.setText(mWalletList.get(position - 1).getName());
                ((WalletHolder) holder).mTextViewWalletBalance.setText("" + mWalletList.get(position - 1).getBalance());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0: {
                return ADD_WALLET;
            }
            default: {
                return WALLET;
            }
        }
    }

    public ChargeWalletRecyclerViewAdapter(List<Wallet> walletList) {

        mWalletList = walletList;
    }

    public void select(int position) {

        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    public void setDataSource(List<Wallet> walletList) {

        mWalletList = walletList;
    }

    @Override
    public int getItemCount() {

        return mWalletList.size() + 1;
    }

    private static class WalletHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewWalletBalance;
        private TextView mTextViewWalletName;
        private ImageView mImageViewCheck;

        private WalletHolder(View itemView) {

            super(itemView);
            mTextViewWalletBalance = itemView.findViewById(R.id.textView_charge_walletbalance);
            mTextViewWalletName = itemView.findViewById(R.id.textview_charge_paymentgateway);
            mImageViewCheck = itemView.findViewById(R.id.imageview_charge_check);
        }
    }

    private static class AddWalletHolder extends RecyclerView.ViewHolder {

        private AddWalletHolder(View itemView) {

            super(itemView);

        }
    }
}
