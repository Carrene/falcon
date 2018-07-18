package de.netalic.falcon.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Wallet;

public class SpinnerAdapter extends ArrayAdapter<Wallet> {

    private LayoutInflater mLayoutInflater;
    private static final int CLOSE = 0;
    private static final int OPEN = 1;

    public SpinnerAdapter(Context mContext, List<Wallet> arrayList) {
        super(mContext, R.layout.spinneritem_charge, arrayList);
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

    public View getCustomView(int position, ViewGroup viewGroup, int type) {

        View view = null;


        if (type == OPEN) {

            view = mLayoutInflater.inflate(R.layout.spinneritem_charge, viewGroup, false);
            TextView textView = view.findViewById(R.id.textview_charge_spinner);
            textView.setText(String.valueOf(getItem(position).getId()));

        } else if (type == CLOSE) {

            view = mLayoutInflater.inflate(R.layout.spinneritem_charge, viewGroup, false);
            TextView textView = view.findViewById(R.id.textview_charge_spinner);
            textView.setText(String.valueOf(getItem(position).getId()));
        }

        return view;
    }
}
