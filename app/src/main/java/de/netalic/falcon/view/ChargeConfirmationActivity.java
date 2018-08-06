package de.netalic.falcon.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import de.netalic.falcon.R;
import de.netalic.falcon.model.Deposit;
import de.netalic.falcon.presenter.ChargeConfirmationPresenter;
import de.netalic.falcon.util.ActivityUtil;

public class ChargeConfirmationActivity extends BaseActivity {

    public static final String ARGUMENT_CHARGE_START = "chargeStart";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {
            throw new RuntimeException("Charge response should not be null!");
        }

        setupBackButton();

        Bundle bundle = getIntent().getExtras();
        Deposit deposit = bundle.getParcelable(ARGUMENT_CHARGE_START);

        ChargeConfirmationFragment chargeConfirmationFragment = (ChargeConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargeconfirmation_fragmentcontainer);
        if (chargeConfirmationFragment == null) {

            chargeConfirmationFragment = ChargeConfirmationFragment.newInstance(deposit);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), chargeConfirmationFragment, R.id.framelayout_chargeconfirmation_fragmentcontainer);
        }
        new ChargeConfirmationPresenter(chargeConfirmationFragment);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chargecompleted_toolbar, menu);
        return true;
    }

    @Override
    protected int getLayoutId() {

        return R.layout.activity_chargeconfirmation;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.chargeconfirmation_chargeconfirmation);
    }
}
