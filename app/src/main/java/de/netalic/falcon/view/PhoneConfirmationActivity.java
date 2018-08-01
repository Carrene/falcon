package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.PhoneConfirmationPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class PhoneConfirmationActivity extends BaseActivity {

    public static final String ARGUMENT_USER = "user";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() == null) {
            throw new RuntimeException("User should not be null");
        }
        User user = getIntent().getExtras().getParcelable(ARGUMENT_USER);



        PhoneConfirmationFragment phoneConfirmationFragment = (PhoneConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_phoneconfirmation_fragmentcontainer);

        if (phoneConfirmationFragment == null) {
            phoneConfirmationFragment = PhoneConfirmationFragment.newInstance(user);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), phoneConfirmationFragment, R.id.framelayout_phoneconfirmation_fragmentcontainer);
        }
        new PhoneConfirmationPresenter(phoneConfirmationFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_phoneconfirmation;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.phoneconfirmation_toolbartitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home: {
                Intent intent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}