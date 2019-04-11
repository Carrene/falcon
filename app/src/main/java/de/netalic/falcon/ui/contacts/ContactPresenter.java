package de.netalic.falcon.ui.contacts;

public class ContactPresenter implements ContactsContract.Presenter {

    private ContactsContract.View mView;


    public ContactPresenter(ContactsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
