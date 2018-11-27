package de.netalic.falcon.ui.load;

import android.content.Intent;
import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.ActivityUtil;

public class LoadCompletedActivity extends BaseActivity {

    private static final String ARGUMENT_RECEIPT = "receipt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {

            throw new RuntimeException("deposit should not be null");
        }
        Receipt receipt = getIntent().getExtras().getParcelable(ARGUMENT_RECEIPT);

        LoadCompletedFragment loadCompletedFragment = (LoadCompletedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargecompleted_fragmentcontainer);
        if (loadCompletedFragment == null) {

            loadCompletedFragment = LoadCompletedFragment.newInstance(receipt);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), loadCompletedFragment, R.id.framelayout_chargecompleted_fragmentcontainer);
        }

        new LoadCompletedPresenter(loadCompletedFragment);

    }


    @Override
    protected int getLayoutId() {

        return R.layout.activity_loadcompleted;
    }

    @Override
    protected String getActionbarTitle() {

        return getString(R.string.loadcompleted_chargecompleted);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
