package de.netalic.falcon.ui.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.Contact;

public class ContactsListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Contact> mContactList;

    public ContactsListRecyclerViewAdapter(List<Contact> contactList) {
        mContactList = contactList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contacts,parent,false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextViewContactName;
        private ImageView mImageViewProfilePicture;
        private ImageView mImageViewAlphaIcon;

        public ContactsViewHolder(View itemView) {
            super(itemView);

            mTextViewContactName=itemView.findViewById(R.id.textview_contacts_contactname);
            mImageViewProfilePicture=itemView.findViewById(R.id.imageview_contacts_profilepicture);
            mImageViewAlphaIcon=itemView.findViewById(R.id.imageview_contacts_alphaicon);
        }
    }
}
