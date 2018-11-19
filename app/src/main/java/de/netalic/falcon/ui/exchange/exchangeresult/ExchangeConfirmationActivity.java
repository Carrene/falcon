package de.netalic.falcon.ui.exchange.exchangeresult;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.exchange.ExchangeFragment;
import de.netalic.falcon.ui.send.SendFragment;
import de.netalic.falcon.util.ActivityUtil;

public class ExchangeConfirmationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        if (getIntent().getExtras() == null) {

            throw new RuntimeException("Exchange should not be null");
        }
        Transaction transaction = getIntent().getExtras().getParcelable(SendFragment.ARGUMENT_TRANSACTION);

        ExchangeConfirmationFragment exchangeConfirmationFragment = (ExchangeConfirmationFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_exchangeconfirmation_fragmentcontainer);
        if (exchangeConfirmationFragment == null) {

            exchangeConfirmationFragment = ExchangeConfirmationFragment.newInstance(transaction);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), exchangeConfirmationFragment, R.id.framelayout_exchangeconfirmation_fragmentcontainer);
        }
        new ExchangeConfirmationPresenter(exchangeConfirmationFragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchangeconfirmation;
    }

    @Override
    protected String getActionbarTitle() {
        return "Exchange Confirmation";
    }
}
