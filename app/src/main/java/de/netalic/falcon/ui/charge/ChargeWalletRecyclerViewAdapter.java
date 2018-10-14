package de.netalic.falcon.ui.charge;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;

public class ChargeWalletRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Wallet> mWalletList;
    private int mSelectedPosition;
    private Callback mCallback;

    private static final int ADD_WALLET = 0;
    private static final int WALLET = 1;


    public interface Callback{

        void navigationToAddWallet();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {

            case ADD_WALLET: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_rowaddwallet, parent, false);
                return new AddWalletHolder(itemView);
            }

            case WALLET: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_rowwallet, parent, false);
                return new WalletHolder(itemView);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (position != mWalletList.size()) {

            if (mSelectedPosition == position) {
                ((WalletHolder) holder).itemView.setAlpha(1);
                ((WalletHolder) holder).mImageViewCheck.setVisibility(View.VISIBLE);
                ((WalletHolder) holder).itemView.setScaleX(1.1f);
                ((WalletHolder) holder).itemView.setScaleY(1.1f);

            } else {
                holder.itemView.setAlpha(0.5f);
                ((WalletHolder) holder).mImageViewCheck.setVisibility(View.GONE);
                ((WalletHolder) holder).itemView.setScaleX(1);
                ((WalletHolder) holder).itemView.setScaleY(1);
            }

            ((WalletHolder) holder).mTextViewWalletName.setText(mWalletList.get(position).getName());
            ((WalletHolder) holder).mTextViewWalletBalance.setText(String.valueOf(Double.valueOf(mWalletList.get(position).getBalance()).longValue()));
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == mWalletList.size()) {
            return ADD_WALLET;

        } else {
            return WALLET;

        }
    }

    public ChargeWalletRecyclerViewAdapter(List<Wallet> walletList,Callback callback) {

        mWalletList = walletList;
        mCallback=callback;
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

    private class AddWalletHolder extends RecyclerView.ViewHolder {

        private AddWalletHolder(View itemView) {

            super(itemView);

            itemView.setOnClickListener(v -> {

            mCallback.navigationToAddWallet();

            });



        }


    }
}
