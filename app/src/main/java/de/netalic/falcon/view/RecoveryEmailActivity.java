package de.netalic.falcon.view;

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

public class RecoveryEmailActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoveryemail);

        //User user = getIntent().getExtras().getParcelable("User");

        Toolbar toolbar = findViewById(R.id.toolbar_recoveryemail);
        setSupportActionBar(toolbar);

        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle(getString(R.string.recoveryemail_toolbartitle));
        }

        RecoveryEmailFragment recoveryEmailFragment = (RecoveryEmailFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_recoveryemail_fragmentcontainer);
        if (recoveryEmailFragment == null) {
            recoveryEmailFragment = RecoveryEmailFragment.newInstance();
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
}