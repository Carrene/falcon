package de.netalic.falcon.ui.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.netalic.falcon.data.model.Contact;

import static io.fabric.sdk.android.Fabric.TAG;

public class ContactPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;


    public ContactPresenter(ContactsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getAllContacts(Context context) {


        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(android.provider.ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        List<Contact> contactList=new ArrayList<>();

        if ((cursor != null ? cursor.getCount() : 0) > 0) {
            while (cursor != null && cursor.moveToNext()) {
                String id = cursor.getString(
                        cursor.getColumnIndex(android.provider.ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(
                        android.provider.ContactsContract.Contacts.DISPLAY_NAME));

                if (cursor.getInt(cursor.getColumnIndex(
                        android.provider.ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursor1 = contentResolver.query(
                            android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (cursor1.moveToNext()) {
                        String phoneNo = cursor1.getString(cursor1.getColumnIndex(
                                android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER));

                        Contact contact=new Contact(phoneNo,name);
                        contactList.add(contact);
                        Log.i(TAG, "Name: " + name);
                        Log.i(TAG, "Phone Number: " + phoneNo);
                    }
                    cursor1.close();
                }
            }
        }
        if(cursor!=null){
            cursor.close();
        }

        mView.setAllContact(contactList);

    }
}
