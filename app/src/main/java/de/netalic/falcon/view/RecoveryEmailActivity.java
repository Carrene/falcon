package de.netalic.falcon.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.RecoveryEmailPresenter;
import de.netalic.falcon.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecoveryEmailActivity extends AppCompatActivity {

    private static final String ARGUMENT_USER = "USER";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoveryemail);

        User user = getIntent().getExtras().getParcelable(ARGUMENT_USER);

        Toolbar toolbar = findViewById(R.id.toolbar_recoveryemail);
        setSupportActionBar(toolbar);

        ActionBar mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setTitle(getString(R.string.recoveryemail_toolbartitle));
        }

        RecoveryEmailFragment recoveryEmailFragment = (RecoveryEmailFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_recoveryemail_fragmentcontainer);
        if (recoveryEmailFragment == null) {
            recoveryEmailFragment = RecoveryEmailFragment.newInstance(user);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), recoveryEmailFragment, R.id.framelayout_recoveryemail_fragmentcontainer);
        }

        new RecoveryEmailPresenter(recoveryEmailFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}