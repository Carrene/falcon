package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.AuthenticationDefinitionPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class AuthenticationDefinitionActivity extends AppCompatActivity {

    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticationdefinition);

        Toolbar toolbar = findViewById(R.id.toolbar_authenticationdefinition);
        setSupportActionBar(toolbar);

        
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setDisplayShowHomeEnabled(true);
            mActionBar.setTitle(getString(R.string.authenticationdefinition_toolbartitle));
        }

        AuthenticationDefinitionFragment authenticationDefinitionFragment = (AuthenticationDefinitionFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_authenticationdefinition_fragmentcontainer);
        if (authenticationDefinitionFragment == null) {
            authenticationDefinitionFragment = AuthenticationDefinitionFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), authenticationDefinitionFragment, R.id.framelayout_authenticationdefinition_fragmentcontainer);
        }

        new AuthenticationDefinitionPresenter(authenticationDefinitionFragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

}