package de.netalic.falcon.ui.registration.phoneinput;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class PhoneInputActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        PhoneInputFragment phoneInputFragment = (PhoneInputFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_phoneinput_fragmentcontainer);
        if (phoneInputFragment == null) {
            phoneInputFragment = PhoneInputFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), phoneInputFragment, R.id.framelayout_phoneinput_fragmentcontainer);
        }

        new PhoneInputPresenter(phoneInputFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_phoneinput;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.registration_toolbartitle);
    }
}