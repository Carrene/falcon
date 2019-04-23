package de.netalic.falcon.ui.notification;

public class NotificationDetailPresenter implements NotificationDetailContract.Presenter {

    private NotificationDetailContract.View mView;

    public NotificationDetailPresenter(NotificationDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
