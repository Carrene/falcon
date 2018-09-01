package de.netalic.falcon.ui.withdraw;

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
import de.netalic.falcon.data.model.Wallet;

public class WithdrawSpinnerAdapter extends ArrayAdapter<Wallet> {

    private LayoutInflater mLayoutInflater;
    private static final int CLOSE = 0;
    private static final int OPEN = 1;


    public WithdrawSpinnerAdapter(Context context, List<Wallet> walletList) {

        super(context, R.layout.spinneritemclose_withdrawtransfer, walletList);
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


        if (type == OPEN) {

            view = mLayoutInflater.inflate(R.layout.spinneritemopen_withdrawtransfer, viewGroup, false);


        } else if (type == CLOSE) {

            view = mLayoutInflater.inflate(R.layout.spinneritemclose_withdrawtransfer, viewGroup, false);
        }

        TextView textViewChargeWalletName = view.findViewById(R.id.textview_withdrawtransfer_walletname);
        textViewChargeWalletName.setText(getItem(position).getName());

        return view;
    }
}
