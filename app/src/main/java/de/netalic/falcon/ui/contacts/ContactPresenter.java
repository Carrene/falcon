package de.netalic.falcon.ui.contacts;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;

import de.netalic.falcon.data.model.Contact;

public class ContactPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;
    private HashMap<String, Contact> contactDetail = new HashMap<>();


    public ContactPresenter(ContactsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public HashMap<String, Contact> getAllContacts(Context context) {
        Cursor cursor = context.getContentResolver().query(android.provider.ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        Integer contactsCount = cursor.getCount();

        String lastPhoneNumber = " ";
        if (contactsCount > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(android.provider.ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCursor = context.getContentResolver().query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCursor.moveToNext()) {

                        String name = pCursor.getString(pCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String phoneNumber = pCursor.getString(pCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("\\s", "");
                        String contactId = pCursor.getString(pCursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
                        String photoUri = pCursor.getString(pCursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.PHOTO_URI));

                        if (!phoneNumber.equalsIgnoreCase(lastPhoneNumber)) {
                            lastPhoneNumber = phoneNumber;
                            contactDetail.put(contactId, new Contact(phoneNumber, name, photoUri));
                        }
                    }
                    pCursor.close();
                }
            }
            cursor.close();
        }

        mView.setAllContact(contactDetail);
        return contactDetail;
    }
}
