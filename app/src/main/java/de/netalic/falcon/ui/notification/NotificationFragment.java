package de.netalic.falcon.ui.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;

public class NotificationFragment extends Fragment implements NotificationContract.View {

    private NotificationContract.Presenter mPresenter;
    private View mRoot;
    private RecyclerView mRecyclerViewNotificationList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_notification,container,false);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
    }

    private void initUiComponent(){

        mRecyclerViewNotificationList=mRoot.findViewById(R.id.recyclerview_notification_notificationlist);
    }

    public static NotificationFragment newInstance() {

        Bundle args = new Bundle();

        NotificationFragment fragment = new NotificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(NotificationContract.Presenter presenter) {

        mPresenter=presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }
}
