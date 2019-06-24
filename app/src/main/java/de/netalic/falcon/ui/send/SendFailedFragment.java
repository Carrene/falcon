package de.netalic.falcon.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.dashboard.DashboardActivity;

public class SendFailedFragment extends Fragment implements SendFailedContract.View {


    private SendFailedContract.Presenter mSendFailedPresenter;
    private View mRoot;
    private Button mButtonDashboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_sendfailed, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponent();
        initListener();
        setHasOptionsMenu(true);
    }


    @Override
    public void setPresenter(SendFailedContract.Presenter presenter) {

        mSendFailedPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }


    private void initUiComponent() {

        mButtonDashboard = mRoot.findViewById(R.id.button_transferfailed_dashborad);

    }

    private void initListener() {


        mButtonDashboard.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sendfailed_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_sendfailed_retry: {

               Intent intent=new Intent(getActivity(),SendActivity.class);
               startActivity(intent);
                break;
            }

        }
        return true;
    }

    public static SendFailedFragment newInstance() {

        return new SendFailedFragment();
    }
}



