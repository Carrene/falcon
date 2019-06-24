package de.netalic.falcon.common.spinneradapter;

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

public class ListCurrencySpinnerAdapter extends ArrayAdapter<Rate> {

    private LayoutInflater mLayoutInflater;
    private final static int OPEN = 1;
    private final static int CLOSE = 0;

    public ListCurrencySpinnerAdapter(@NonNull Context context, @NonNull List<Rate> objects) {
        super(context, R.layout.spinnerlistcurrency_common, objects);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        if (type == 0) {

            view = mLayoutInflater.inflate(R.layout.spinnerlistcurrencyclose_common, viewGroup,false);


        } else {

            view = mLayoutInflater.inflate(R.layout.spinnerlistcurrencyopen_common, viewGroup,false);
        }

        TextView textViewWalletName = view.findViewById(R.id.textview_common_currencycode);
        textViewWalletName.setText(getItem(position).getCurrencyCode());

        return view;
    }
}
