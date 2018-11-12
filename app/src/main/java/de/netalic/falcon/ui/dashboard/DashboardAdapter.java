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
    private int mLastViewPosition = 1;

    public DashboardAdapter(List<Wallet> data){
        mData = data;
    }

    public void setData(List<Wallet> data){
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallets, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mData.size() != 0){
            position = position % mData.size();
            holder.mTextViewName.setText(mData.get(position).getName() + " Wallet");
            holder.mTextViewCurrencySymbol.setText(mData.get(position).getCurrencySymbol());
            holder.mTextViewBalance.setText(String.valueOf(mData.get(position).getBalance()));
            holder.mTextViewAddress.setText(mData.get(position).getAddress());
//            if (mLastViewPosition != position){
//                holder.itemView.getLayoutParams().height = 600;
//            }else {
//                mLastViewPosition = position;
//                holder.itemView.getLayoutParams().height = 400;
//            }
        }

    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
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
