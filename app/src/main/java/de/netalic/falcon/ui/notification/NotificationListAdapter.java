package de.netalic.falcon.ui.notification;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import de.netalic.falcon.R;

public class NotificationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View mRoot;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mRoot= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification,parent,false);
        return new Notification(mRoot);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    private static class Notification extends RecyclerView.ViewHolder {

        private CircularImageView mCircularImageView;
        private TextView mTextViewDescribe;
        private TextView mTextViewTime;
        private TextView mTextViewDate;

        public Notification(View itemView) {
            super(itemView);

            mCircularImageView=itemView.findViewById(R.id.circularimageview_notification_profilepicture);
            mTextViewDate=itemView.findViewById(R.id.textview_notification_date);
            mTextViewTime=itemView.findViewById(R.id.textview_notification_time);
            mTextViewDescribe=itemView.findViewById(R.id.textview_notification_describe);
        }
    }
}
