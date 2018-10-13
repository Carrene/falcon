package de.netalic.falcon.ui.setting.basecurrency;

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

public class CurrenciesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Currency> mCurrencyList;
    private String mBaseCurrency;
    private Callback mCallback;
    private CurrencyViewHolder mCurrencyViewHolder;

    public interface Callback {

        void changeBaseCurrency(String currencyCode);
    }

    public CurrenciesRecyclerViewAdapter(List<Currency> currencyList, String baseCurrency, Callback callback) {

        mCurrencyList = currencyList;
        mBaseCurrency = baseCurrency;
        mCallback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_currencies, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        mCurrencyViewHolder = (CurrencyViewHolder) holder;

        mCurrencyViewHolder.mTextViewCurrency.setText(mCurrencyList.get(position).getCode());

        if (mCurrencyList.get(position).getCode().equals(mBaseCurrency)) {

            mCurrencyViewHolder.mImageViewCheckTik.setVisibility(View.VISIBLE);
        } else {

            mCurrencyViewHolder.mImageViewCheckTik.setVisibility(View.GONE);

        }
    }

    @Override
    public int getItemCount() {
        return mCurrencyList.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewCurrency;
        private ImageView mImageViewCheckTik;

        public CurrencyViewHolder(View itemView) {
            super(itemView);

            mTextViewCurrency = itemView.findViewById(R.id.textview_basecurrency_currency);
            mImageViewCheckTik = itemView.findViewById(R.id.imageview_basecurrency_tik);

            itemView.setOnClickListener(v -> {

                mCallback.changeBaseCurrency(mCurrencyList.get(getLayoutPosition()).getCode());

            });

        }
    }

}

