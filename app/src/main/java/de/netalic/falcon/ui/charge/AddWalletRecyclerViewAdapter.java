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
import de.netalic.falcon.data.model.Currency;


public class AddWalletRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Currency>mCurrencyList;
    private Callback mCallback;
    private String mSelectedCurrency;


    public AddWalletRecyclerViewAdapter(List<Currency> currencyList, Callback callback,String selectedCurrency) {
        mCurrencyList = currencyList;
        mCallback = callback;
        mSelectedCurrency=selectedCurrency;
    }

    public interface Callback{

        void setCurrency(String currency);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_currencieslistaddwallet,parent,false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((CurrencyViewHolder)holder).mTextViewCurrencyName.setText(mCurrencyList.get(position).getCode());
        if (mSelectedCurrency!=null && mSelectedCurrency.equals(mCurrencyList.get(position).getCode())){

            ((CurrencyViewHolder)holder).mImageViewTick.setVisibility(View.VISIBLE);
        }

        else {

            ((CurrencyViewHolder)holder).mImageViewTick.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextViewCurrencyName;
        private ImageView mImageViewTick;

        public CurrencyViewHolder(View itemView) {
            super(itemView);

            mTextViewCurrencyName=itemView.findViewById(R.id.textview_rowaddwallet_currencyname);
            mImageViewTick=itemView.findViewById(R.id.imageview_rowaddwallet_selection);

            itemView.setOnClickListener(v -> {

                mCallback.setCurrency(mCurrencyList.get(getItemCount()).getCode());
            });
        }
    }
}
