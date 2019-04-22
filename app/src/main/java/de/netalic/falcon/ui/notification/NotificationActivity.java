package de.netalic.falcon.ui.notification;

import android.os.Bundle;
import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;
import de.netalic.falcon.util.NavigationDrawerUtil;

public class NotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();
        NavigationDrawerUtil.getDrawer(this,getToolbar(),1);

        NotificationFragment notificationFragment=(NotificationFragment) getSupportFragmentManager().findFragmentById(R.id.farmelayout_notification_fragmentcountainer);

        if (notificationFragment==null){

            notificationFragment=NotificationFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),notificationFragment,R.id.farmelayout_notification_fragmentcountainer);
        }

        new NotificationPresenter(notificationFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notification;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.notification_toolbar);
    }
}