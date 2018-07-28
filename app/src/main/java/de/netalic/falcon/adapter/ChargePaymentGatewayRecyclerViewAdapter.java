package de.netalic.falcon.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import de.netalic.falcon.R;

public class ChargePaymentGatewayRecyclerViewAdapter extends RecyclerView.Adapter<ChargePaymentGatewayRecyclerViewAdapter.Holder> {

    private List<Integer> mPaymentGatewayList;
    private int mSelectedPosition;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.charge_row_paymentgateway, parent, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        if (mSelectedPosition == position) {
            holder.itemView.setAlpha(1);
            holder.mImageViewCheck.setVisibility(View.VISIBLE);
            holder.itemView.setScaleX(1.1f);
            holder.itemView.setScaleY(1.1f);

        } else {
            holder.itemView.setAlpha(0.5f);
            holder.mImageViewCheck.setVisibility(View.GONE);
            holder.itemView.setScaleX(1f);
            holder.itemView.setScaleY(1f);
        }

        holder.mImageViewPaymentGatewayIcon.setImageResource(mPaymentGatewayList.get(position));

    }

    public ChargePaymentGatewayRecyclerViewAdapter(List<Integer> paymentGatewayList) {

        mPaymentGatewayList = paymentGatewayList;
    }

    public void select(int position) {

        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    public void setDataSource(List<Integer> paymentGatewayList) {

        mPaymentGatewayList = paymentGatewayList;
    }

    @Override
    public int getItemCount() {

        return mPaymentGatewayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        private ImageView mImageViewPaymentGatewayIcon;
        private ImageView mImageViewCheck;


        private Holder(View itemView) {

            super(itemView);
            mImageViewPaymentGatewayIcon = itemView.findViewById(R.id.imageview_charge_paymentgateway);
            mImageViewCheck = itemView.findViewById(R.id.imageview_charge_check);

        }
    }
}

