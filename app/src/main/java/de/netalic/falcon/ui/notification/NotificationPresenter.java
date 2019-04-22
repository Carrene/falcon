package de.netalic.falcon.ui.notification;

public class NotificationPresenter implements NotificationContract.Presenter {


    private NotificationContract.View mView;

    public NotificationPresenter(NotificationContract.View view) {
           mView = view;
           mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
