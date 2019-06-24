package de.netalic.falcon.ui.withdraw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Rate;

public class WithdrawAmountSpinnerAdapter extends ArrayAdapter<Rate> {

    private LayoutInflater mLayoutInflater;
    private static final int CLOSE = 0;
    private static final int OPEN = 1;


    public WithdrawAmountSpinnerAdapter(Context mContext, List<Rate> rateList) {

        super(mContext, R.layout.spinneritemclose_dashbaord, rateList);
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return getCustomView(position, parent, CLOSE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return getCustomView(position, parent, OPEN);
    }

    private View getCustomView(int position, ViewGroup viewGroup, int type) {

        View view = null;


        if (type == OPEN) {

            view = mLayoutInflater.inflate(R.layout.spinnerlistcurrencyopen_common, viewGroup, false);


        } else if (type == CLOSE) {

            view = mLayoutInflater.inflate(R.layout.spinnerlistcurrencyclose_common, viewGroup, false);
        }

        TextView textViewChargeWalletName = view.findViewById(R.id.textview_common_currencycode);
        textViewChargeWalletName.setText(getItem(position).getCurrencyCode());

        return view;
    }
}
