package de.netalic.falcon.ui.addresses;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.withdraw.WithdrawQrCompletedActivity;
import de.netalic.falcon.util.QrCodeUtil;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.crypto.CryptoUtil;

public class AddressesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Wallet> mWalletList;
    private Context mContext;


    public AddressesRecyclerViewAdapter(List<Wallet> walletList, Context context) {
        mWalletList = walletList;
        mContext = context;
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
            mImageViewQrCode = itemView.findViewById(R.id.imageview_addresses_qrcode);
            mImageViewShare = itemView.findViewById(R.id.imageview_addresses_share);
            mImageViewQrCode.setOnClickListener(this);
            mImageViewShare.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.imageview_addresses_share) {

                ClipboardManager clipBoard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Wallet Address", mWalletList.get(getAdapterPosition()).getAddress());
                clipBoard.setPrimaryClip(clip);
                SnackbarUtil.showSnackbar(v, mContext.getString(R.string.addresses_copied), mContext);

            }

            if (v.getId() == R.id.imageview_addresses_qrcode) {

                String walletAddress = mWalletList.get(getAdapterPosition()).getAddress();
                setQrCode(walletAddress);


            }


        }

        public void setQrCode(String qrCodeContent) {

            Bitmap bitmap = null;
            try {
                bitmap = QrCodeUtil.generateQrCode(qrCodeContent, 300, 300);
                navigateToQrCodeAddressesActivity(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        private void navigateToQrCodeAddressesActivity(Bitmap bitmap) {

            Intent intent = new Intent(mContext, QrCodeAddressesActivity.class);
            intent.putExtra("qr", bitmap);
            intent.putExtra("currencyCode",mWalletList.get(getAdapterPosition()).getCurrencyCode());
            mContext.startActivity(intent);
        }

    }

}
