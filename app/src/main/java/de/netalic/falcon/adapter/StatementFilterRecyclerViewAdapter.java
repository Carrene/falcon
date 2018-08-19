package de.netalic.falcon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

public class StatementFilterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TEXT_SWITCH = 0;
    private static final int TEXT_RADIOBUTTON = 1;
    private static final int HEADER = 2;


    public StatementFilterRecyclerViewAdapter() {

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 0;
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0: {
                return TEXT_SWITCH;
            }

            case 1: {
                return TEXT_RADIOBUTTON;
            }
        }

        return -1;
    }

    private int getActualPosition(int position) {

        if (position == 2) {
            return 1;
        }

        return -1;
    }

    private static class TextSwitchHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private Switch mSwitch;

        private TextSwitchHolder(View itemView) {

            super(itemView);

        }
    }

    private static class TextRadioButtonHolder extends RecyclerView.ViewHolder {

        private Text mTextView;
        private RadioButton mRadioButton;

        private TextRadioButtonHolder(View itemView) {

            super(itemView);

        }
    }
}
