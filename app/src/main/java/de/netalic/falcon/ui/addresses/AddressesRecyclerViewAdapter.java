package de.netalic.falcon.ui.addresses;

import android.content.Context;
import android.content.Intent;
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

public class AddressesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private List<Wallet> mWalletList;
    private Context mContext;



    public AddressesRecyclerViewAdapter(List<Wallet> walletList, Context context) {
        mWalletList = walletList;
        mContext=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_addresses, parent, false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        WalletViewHolder walletViewHolder = (WalletViewHolder) holder;
        Wallet wallet = mWalletList.get(position);
        walletViewHolder.mTextViewWalletName.setText(wallet.getName());
        walletViewHolder.mTextViewBalance.setText(wallet.getCurrencySymbol() + " " + String.valueOf(wallet.getBalance()));
        walletViewHolder.mTextViewAddress.setText(wallet.getAddress());

    }

    @Override
    public int getItemCount() {
        return mWalletList.size();
    }



    private class WalletViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextViewWalletName;
        private TextView mTextViewBalance;
        private TextView mTextViewAddress;
        private ImageView mImageViewShare;
        private ImageView mImageViewQrCode;

        private WalletViewHolder(View itemView) {
            super(itemView);
            mTextViewWalletName = itemView.findViewById(R.id.textview_addresses_walletname);
            mTextViewBalance = itemView.findViewById(R.id.textview_addresses_balance);
            mTextViewAddress = itemView.findViewById(R.id.textview_addresses_walletaddress);
            mImageViewQrCode=itemView.findViewById(R.id.imageview_addresses_qrcode);
            mImageViewShare =itemView.findViewById(R.id.imageview_addresses_share);
            mImageViewQrCode.setOnClickListener(this);
            mImageViewShare.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId()==R.id.imageview_addresses_share){

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Wallet Address");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, mWalletList.get(getAdapterPosition()).getAddress());
                mContext.startActivity(Intent.createChooser(sharingIntent,"share"));

            }

            if (v.getId()==R.id.imageview_addresses_qrcode){


            }

        }
    }



}
