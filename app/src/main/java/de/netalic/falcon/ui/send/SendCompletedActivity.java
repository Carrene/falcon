package de.netalic.falcon.ui.send;

import android.content.Intent;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Transaction;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;

public class SendCompletedActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().getExtras()==null){

            throw new RuntimeException("transaction should not be null");
        }

        Transaction transaction=getIntent().getExtras().getParcelable(SendFragment.ARGUMENT_TRANSACTION);
        SendCompletedFragment sendCompletedFragment=(SendCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_transfercompleted_fragmentcontainer);
        if (sendCompletedFragment==null){

            sendCompletedFragment=SendCompletedFragment.newInstance(transaction);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),sendCompletedFragment,R.id.framelayout_transfercompleted_fragmentcontainer);
        }
        new SendCompletedPresenter(sendCompletedFragment);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class).
                setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sendcompleted;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.sendcompleted_toolbar);
    }
}
