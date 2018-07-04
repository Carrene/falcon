package de.netalic.falcon.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.PhoneConfirmationPresenter;
import de.netalic.falcon.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PhoneConfirmationActivity extends AppCompatActivity {

    public static final String ARGUMENT_USER = "USER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneconfirmation);

        User user = getIntent().getExtras().getParcelable(ARGUMENT_USER);

        Toolbar toolbar = findViewById(R.id.toolbar_phoneconfirmation);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle(getString(R.string.phoneconfirmation_toolbartitle));
        }

        PhoneConfirmationFragment phoneConfirmationFragment = (PhoneConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_phoneconfirmation_fragmentcontainer);
        if (phoneConfirmationFragment == null) {
            phoneConfirmationFragment = PhoneConfirmationFragment.newInstance(user);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), phoneConfirmationFragment, R.id.framelayout_phoneconfirmation_fragmentcontainer);
        }

        new PhoneConfirmationPresenter(phoneConfirmationFragment);
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}