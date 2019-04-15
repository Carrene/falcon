package de.netalic.falcon.ui.contacts;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import de.netalic.falcon.data.model.Contact;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ContactsContract {

    interface View extends BaseView<Presenter> {

        void setAllContact(Map<String, Contact> contactList);


    }

    interface Presenter extends BasePresenter {

        HashMap<String, Contact> getAllContacts(Context context);

    }


}
