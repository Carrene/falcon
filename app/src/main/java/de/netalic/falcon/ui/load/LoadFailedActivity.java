package de.netalic.falcon.ui.load;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Receipt;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class LoadFailedActivity extends BaseActivity {

    private static final String ARGUMENT_RECEIPT = "receipt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() == null) {

            throw new RuntimeException("deposit should not null");
        }

        Receipt receipt = getIntent().getExtras().getParcelable(ARGUMENT_RECEIPT);


        LoadFailedFragment loadFailedFragment = (LoadFailedFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_chargefailed_fragmentcontainer);
        if (loadFailedFragment == null) {
            loadFailedFragment = LoadFailedFragment.newInstance(receipt);
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), loadFailedFragment, R.id.framelayout_chargefailed_fragmentcontainer);
        }

        new LoadFailedPresenter(loadFailedFragment);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_loadfailed;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.laodfailed_failed);
    }


}
