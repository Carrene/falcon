package de.netalic.falcon.ui.notification;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class NotificationDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        NotificationDetailFragment notificationDetailFragment = (NotificationDetailFragment) getSupportFragmentManager().findFragmentById(R.id.farmelayout_notificationdetail_fragmentcountainer);

        if (notificationDetailFragment == null) {
            notificationDetailFragment = NotificationDetailFragment.newInstance();

            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), notificationDetailFragment, R.id.farmelayout_notificationdetail_fragmentcountainer);

            new NotificationDetailPresenter(notificationDetailFragment);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notificationdetail;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.notification_toolbar);
    }
}
