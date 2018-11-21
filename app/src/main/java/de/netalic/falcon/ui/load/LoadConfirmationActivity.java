package de.netalic.falcon.ui.load;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class LoadConfirmationActivity extends BaseActivity {

    public static final String ARGUMENT_CHARGE_START = "chargeStart";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {
            throw new RuntimeException("Charge response should not be null!");
        }

        setupBackButton();

        Bundle bundle = getIntent().getExtras();
        Transaction transaction = bundle.getParcelable(ARGUMENT_CHARGE_START);

        LoadConfirmationFragment loadConfirmationFragment = (LoadConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargeconfirmation_fragmentcontainer);
        if (loadConfirmationFragment == null) {

            loadConfirmationFragment = LoadConfirmationFragment.newInstance(transaction);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), loadConfirmationFragment, R.id.framelayout_chargeconfirmation_fragmentcontainer);
        }
        new LoadConfirmationPresenter(loadConfirmationFragment);

    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_loadconfirmation;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.loadconfirmation_chargeconfirmation);
    }
}
