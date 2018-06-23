package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.SignInPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        SignInFragment signInFragment = (SignInFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_signin_fragmentcontainer);
        if (signInFragment == null) {
            signInFragment = SignInFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), signInFragment, R.id.framelayout_signin_fragmentcontainer);
        }

        new SignInPresenter(signInFragment);

    }

}
