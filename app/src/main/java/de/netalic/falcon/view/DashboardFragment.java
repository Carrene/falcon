package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.DashboardPresenterContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class DashboardFragment extends Fragment implements DashboardPresenterContract.View {

    private DashboardPresenterContract.Presenter mPresenter;
    private View mRoot;
    private TextView mPhoneNumber;
    private TextView mEmail;
    private User mUser;
    private static final String ARGUMENT_USER = "USER";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_dashboard, null);
        mUser = getArguments().getParcelable(ARGUMENT_USER);
        setHasOptionsMenu(true);
        initUiComponents();
//        setPhoneNumber();
//        setEmail();
        return mRoot;
    }

    public static DashboardFragment newInstance(User user) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_USER, user);
        DashboardFragment dashboardFragment = new DashboardFragment();
        dashboardFragment.setArguments(bundle);
        return dashboardFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }


    @Override
    public void setPresenter(DashboardPresenterContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_dashboard_toolbar, menu);
    }

    public void initUiComponents() {

        mPhoneNumber = mRoot.findViewById(R.id.textview_dashboard_phonenumbernavigationheader);
        mEmail = mRoot.findViewById(R.id.textview_dashboard_emailnavigationheader);

    }

    public void setPhoneNumber(){

        mPhoneNumber.setText(mUser.getPhone());

    }

    public void setEmail(){

        mEmail.setText(mUser.getEmail());
    }

}
