package de.netalic.falcon.ui.transaction.transactionhistoryfilters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.model.State;

public class TransactionHistoryFilterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TEXT_SWITCH = 0;
    private static final int TEXT_RADIOBUTTON = 1;
    private static final int HEADER = 2;

    private List<State> mStateList;

    private List<String> mHeaderList;

    public TransactionHistoryFilterRecyclerViewAdapter(List<State> stateList, List<String> headerList) {

        mStateList = stateList;
        mHeaderList = headerList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {

            case TEXT_SWITCH: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowtextviewswitch_transactionfilters, parent, false);
                return new TextSwitchHolder(itemView);
            }

            case TEXT_RADIOBUTTON: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowtextviewradiobutton_transactionfilters, parent, false);
                return new TextRadioButtonHolder(itemView);
            }

            case HEADER: {
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowheader_transactionfilters, parent, false);
                return new HeaderHolder(itemView);
            }
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof TextSwitchHolder) {
            ((TextSwitchHolder) holder).mTextView.setText(mStateList.get(getActualPosition(position)).getUiKey());
        } else if (holder instanceof TextRadioButtonHolder) {
            ((TextRadioButtonHolder) holder).mTextView.setText(mStateList.get(getActualPosition(position)).getUiKey());
        } else if (holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).mTextView.setText(mHeaderList.get(getHeaderPosition(position)));
        }
    }

    @Override
    public int getItemCount() {

        return mStateList.size() + 3;
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {

            case 0:
            case 3:
            case 8:
                return HEADER;
            case 1:
            case 2:
            case 4:
            case 5:
            case 6:
            case 7:
                return TEXT_SWITCH;
            case 9:
            case 10:
            case 11:
                return TEXT_RADIOBUTTON;
        }
        return -1;
    }

    private int getActualPosition(int position) {

        switch (position) {
            case 1:
            case 2: {
                return position - 1;
            }

            case 4:
            case 5:
            case 6:
            case 7:
                return position - 2;

            case 9:
            case 10:
                return position - 3;
        }
        return position - 2;
    }

    private int getHeaderPosition(int position) {

        switch (position) {
            case 0:
                return 0;
            case 3:
                return 1;
            case 8:
                return 2;
        }

        return -1;
    }

    private static class TextSwitchHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private SwitchCompat mSwitch;

        private TextSwitchHolder(View itemView) {

            super(itemView);
            mTextView = itemView.findViewById(R.id.textview_transactionfiltersrow_title);
            mSwitch = itemView.findViewById(R.id.switchcompat_transactionfiltersrow_value);

        }
    }

    private static class TextRadioButtonHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private RadioButton mRadioButton;

        private TextRadioButtonHolder(View itemView) {

            super(itemView);
            mTextView = itemView.findViewById(R.id.textview_transactionfiltersrow_title);
            mRadioButton = itemView.findViewById(R.id.radiobutton_transactionfiltersrow_value);
        }
    }

    private static class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        private HeaderHolder(View itemView) {

            super(itemView);
            mTextView = itemView.findViewById(R.id.textview_transactionfiltersrow_header);
        }
    }
}
