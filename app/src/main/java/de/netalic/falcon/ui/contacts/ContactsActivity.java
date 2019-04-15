package de.netalic.falcon.ui.contacts;

import android.os.Bundle;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.util.ActivityUtil;

public class ContactsActivity extends BaseActivity {


    private ContactsFragment mContactsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBackButton();

        mContactsFragment=(ContactsFragment) getSupportFragmentManager().findFragmentById(R.id.framelayout_contacts_fragmentcontainer);
        if (mContactsFragment==null){

            mContactsFragment=ContactsFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(),mContactsFragment,R.id.framelayout_contacts_fragmentcontainer);
        }
        new ContactPresenter(mContactsFragment);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contacts;
    }

    @Override
    protected String getActionbarTitle() {
        return getString(R.string.contacts_toolbar);
    }
}
