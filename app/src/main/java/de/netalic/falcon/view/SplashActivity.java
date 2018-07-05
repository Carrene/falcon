package de.netalic.falcon.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.SplashPresenter;
import de.netalic.falcon.util.ActivityUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment = (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_signin_fragmentcontainer);
        if (splashFragment == null) {
            splashFragment = SplashFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), splashFragment, R.id.framelayout_signin_fragmentcontainer);
        }

        new SplashPresenter(splashFragment);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
