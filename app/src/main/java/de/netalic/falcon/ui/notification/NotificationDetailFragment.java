package de.netalic.falcon.ui.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import de.netalic.falcon.R;

public class NotificationDetailFragment extends Fragment implements NotificationDetailContract.View {

    private View mRoot;
    private TextView mTextViewName;
    private TextView mTextViewDate;
    private TextView mTextViewTime;
    private TextView mTextViewDescribe;
    private CircularImageView mCircularImageView;
    private NotificationDetailContract.Presenter mPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_notificaitondetail, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        iniUiComponent();
    }

    @Override
    public void setPresenter(NotificationDetailContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    private void iniUiComponent() {

        mTextViewName = mRoot.findViewById(R.id.textview_notificationdetail_name);
        mTextViewDate = mRoot.findViewById(R.id.textview_notificationdetail_date);
        mTextViewTime = mRoot.findViewById(R.id.textview_notificationdetail_time);
        mTextViewDescribe = mRoot.findViewById(R.id.textview_notificationdetail_describe);
        mCircularImageView = mRoot.findViewById(R.id.circularimageview_notificationdetail_profilepicture);
    }

    public static NotificationDetailFragment newInstance() {

        Bundle args = new Bundle();

        NotificationDetailFragment fragment = new NotificationDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
