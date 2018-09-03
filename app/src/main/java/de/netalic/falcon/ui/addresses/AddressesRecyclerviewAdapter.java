package de.netalic.falcon.ui.addresses;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;

public class AddressesRecyclerviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Wallet> mWalletList = new ArrayList<>();

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

    private class WalletViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewWalletName;
        private TextView mTextViewBalance;
        private TextView mTextViewAddress;


        private WalletViewHolder(View itemView) {
            super(itemView);
            mTextViewWalletName = itemView.findViewById(R.id.textview_addresses_walletname);
            mTextViewBalance = itemView.findViewById(R.id.textview_addresses_balance);
            mTextViewAddress = itemView.findViewById(R.id.textview_addresses_walletaddress);

        }
    }

}
