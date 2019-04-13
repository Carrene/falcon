package de.netalic.falcon.ui.contacts;

import android.content.Context;

import java.util.List;

import de.netalic.falcon.data.model.Contact;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface ContactsContract {

    interface View extends BaseView<Presenter>{

        void setAllContact(List<Contact> contactList);


    }

    interface Presenter extends BasePresenter{

        void getAllContacts(Context context);

    }


}
