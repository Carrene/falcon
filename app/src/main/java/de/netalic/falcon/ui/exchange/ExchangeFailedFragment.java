package de.netalic.falcon.ui.exchange;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.netalic.falcon.R;
import de.netalic.falcon.ui.dashboard.DashboardActivity;

public class ExchangeFailedFragment extends Fragment implements ExchangeFailedContract.View {


    private ExchangeFailedContract.Presenter mExchangeFailedPresenter;
    private View mRoot;
    private Button mButtonDashboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_exchangefailed, container, false);
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
    public void setPresenter(ExchangeFailedContract.Presenter presenter) {

        mExchangeFailedPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }


    private void initUiComponent() {

        mButtonDashboard = mRoot.findViewById(R.id.button_exchangefailed_dashborad);

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
        inflater.inflate(R.menu.menu_exchangefailed_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item_exchangefailed_retry: {

                Intent intent = new Intent(getActivity(), ExchangeActivity.class);
                startActivity(intent);
                break;
            }

        }
        return true;
    }

    public static ExchangeFailedFragment newInstance() {

        return new ExchangeFailedFragment();
    }
}



