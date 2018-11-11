package de.netalic.falcon.ui.dashboard;

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

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<Wallet> mData;

    public DashboardAdapter(List<Wallet> data){
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTextViewName.setText(mData.get(position).getName());
        holder.mTextViewCurrencySymbol.setText(mData.get(position).getCurrencySymbol());
        holder.mTextViewBalance.setText(String.valueOf(mData.get(position).getBalance()));
        holder.mTextViewAddress.setText(mData.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextViewName;
        private TextView mTextViewBalance;
        private TextView mTextViewCurrencySymbol;
        private TextView mTextViewAddress;
        private ImageView mImageViewQrCode;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewName = itemView.findViewById(R.id.textview_dashboard_walletname);
            mTextViewBalance = itemView.findViewById(R.id.textview_dashboard_walletbalance);
            mTextViewCurrencySymbol = itemView.findViewById(R.id.textview_dashboard_walletcurrencysymbol);
            mTextViewAddress = itemView.findViewById(R.id.textview_dashboard_walletaddress);
            mImageViewQrCode = itemView.findViewById(R.id.imageview_dashboard_qrcode);
        }
    }
}
