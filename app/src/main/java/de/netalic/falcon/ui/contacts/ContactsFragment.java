package de.netalic.falcon.ui.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Contact;
import de.netalic.falcon.util.SnackbarUtil;

public class ContactsFragment extends Fragment implements ContactsContract.View {

    private View mRoot;
    private ContactsContract.Presenter mPresenter;
    private List<Contact> mContactList;
    private Map<String, Contact> mContactMap;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_contacts, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getContext().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {

            mPresenter.getAllContacts(getContext());
        }
    }

    @Override
    public void setPresenter(ContactsContract.Presenter presenter) {

        mPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static ContactsFragment newInstance() {

        Bundle args = new Bundle();

        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setAllContact(Map<String, Contact> contactMap) {

        mContactMap = contactMap;

        List<Contact> list = new ArrayList<>(contactMap.values());

        if (list.size() > 0) {
            Collections.sort(list, (object1, object2) ->
                    object1.getName().compareTo(object2.getName()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mPresenter.getAllContacts(getContext());
            } else {
                SnackbarUtil.showSnackbar(mRoot, getString(R.string.everywhere_permissiondenied), getContext());
            }
        }
    }
}
