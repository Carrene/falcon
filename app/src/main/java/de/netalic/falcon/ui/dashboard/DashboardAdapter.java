package de.netalic.falcon.ui.dashboard;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.util.QrCodeUtil;

public class DashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Wallet> mData;
    private int mSelectedPosition;
    private Callback mCallback;
    private static final int ADD_WALLET = 0;
    private static final int WALLET = 1;

    public DashboardAdapter(List<Wallet> data, Callback callback) {
        mData = data;
        mCallback = callback;
    }

    public void setData(List<Wallet> data) {
        mData = data;
        notifyDataSetChanged();
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
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_wallets, parent, false);
                return new ViewHolder(itemView);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mData.size() != 0 && position != mData.size()) {

            if (mSelectedPosition == position) {

                holder.itemView.setScaleY(1f);

            } else {

                holder.itemView.setScaleY(0.8f);
            }

            ((ViewHolder) holder).mTextViewName.setText(mData.get(position).getName() + " Wallet");
            ((ViewHolder) holder).mTextViewCurrencySymbol.setText(mData.get(position).getCurrencySymbol());
            ((ViewHolder) holder).mTextViewBalance.setText(String.valueOf(mData.get(position).getBalance()));
            ((ViewHolder) holder).mTextViewAddress.setText(mData.get(position).getAddress());
            try {
                Bitmap bitmap = QrCodeUtil.generateQrCode(mData.get(position).getAddress(), 48, 48);
                bitmap = Bitmap.createBitmap(bitmap, 12, 12, 24, 24);
                ((ViewHolder) holder).mImageViewQrCode.setImageBitmap(bitmap);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position == mData.size()) {
            return ADD_WALLET;

        } else {
            return WALLET;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public void select(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    public interface Callback {
        void navigationToAddWallet();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

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

    private class AddWalletHolder extends RecyclerView.ViewHolder {

        private AddWalletHolder(View itemView) {

            super(itemView);

            itemView.setOnClickListener(v -> {
                mCallback.navigationToAddWallet();
            });
        }


    }
}
