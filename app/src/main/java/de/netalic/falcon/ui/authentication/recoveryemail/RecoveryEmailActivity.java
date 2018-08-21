package de.netalic.falcon.ui.authentication.recoveryemail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class RecoveryEmailActivity extends BaseActivity {

    private static final String ARGUMENT_USER = "USER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() == null) {
            throw new RuntimeException("User should not be null");
        }

        User user = getIntent().getExtras().getParcelable(ARGUMENT_USER);

        RecoveryEmailFragment recoveryEmailFragment = (RecoveryEmailFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_recoveryemail_fragmentcontainer);

        if (recoveryEmailFragment == null) {
            recoveryEmailFragment = RecoveryEmailFragment.newInstance(user);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), recoveryEmailFragment, R.id.framelayout_recoveryemail_fragmentcontainer);
        }
        new RecoveryEmailPresenter(recoveryEmailFragment);
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_recoveryemail;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.recoveryemail_toolbartitle);
    }
}