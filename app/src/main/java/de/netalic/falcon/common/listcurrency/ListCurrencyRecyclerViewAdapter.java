package de.netalic.falcon.common.listcurrency;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Rate;


public class ListCurrencyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Rate> mRateList;
    private Callback mCallback;
    private String mSelectedCurrency;


    public ListCurrencyRecyclerViewAdapter(List<Rate> currencyList, String selectedCurrency, Callback callback) {
        mRateList = currencyList;
        mCallback = callback;
        mSelectedCurrency = selectedCurrency;

    }

    public interface Callback {

        void setCurrency(Rate currency);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_currencieslistaddwallet, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((CurrencyViewHolder) holder).mTextViewCurrencyName.setText(mRateList.get(position).getCurrencyCode());
        if (mSelectedCurrency != null && mSelectedCurrency.equals(mRateList.get(position).getCurrencyCode())) {

            ((CurrencyViewHolder) holder).mImageViewTick.setVisibility(View.VISIBLE);
        } else {

            ((CurrencyViewHolder) holder).mImageViewTick.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return mRateList.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewCurrencyName;
        private ImageView mImageViewTick;

        public CurrencyViewHolder(View itemView) {
            super(itemView);

            mTextViewCurrencyName = itemView.findViewById(R.id.textview_rowaddwallet_currencyname);
            mImageViewTick = itemView.findViewById(R.id.imageview_rowaddwallet_selection);

            itemView.setOnClickListener(v -> {

                mCallback.setCurrency(mRateList.get(getLayoutPosition()));
            });
        }
    }
}
