package de.netalic.falcon.ui.authentication.authentication;

import android.content.Context;
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
import android.widget.EditText;

import de.netalic.falcon.R;
import de.netalic.falcon.util.SnackbarUtil;

public class AuthenticationPasswordFragment extends Fragment implements AuthenticationContract.View {

    private AuthenticationContract.Presenter mAuthenticationPresenter;
    private NavigateToDashboardCallback mNavigateToDashboardCallback;
    private int mAuthenticationType;
    private View mRoot;
    private EditText mEditTextPassword;


    interface NavigateToDashboardCallback {

        void checkCredentialValue(String credentialValue);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationpassword, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
        initUiListener();
    }

    @Override
    public void setPresenter(AuthenticationContract.Presenter presenter) {

        mAuthenticationPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    public static AuthenticationPasswordFragment newInstance() {

        AuthenticationPasswordFragment fragment = new AuthenticationPasswordFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NavigateToDashboardCallback) {

            mNavigateToDashboardCallback = (NavigateToDashboardCallback) context;
        } else {

            throw new ClassCastException(context.toString() + " must implement NavigateToDashboardCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigateToDashboardCallback = null;
    }

    private void initUiComponent() {

        mEditTextPassword = mRoot.findViewById(R.id.edittext_authentication_entercode);

    }

    private void initUiListener() {

        mEditTextPassword.setOnClickListener(v -> {


        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_everywhere_thathastick, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_everywhere_done: {

                if (mEditTextPassword.toString().equals("")) {

                    SnackbarUtil.showSnackbar(mRoot, "Please fill the box", getContext());

                } else {

                    navigateToDashboard(mEditTextPassword.getText().toString());
                }


            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void navigateToDashboard(String credentialValue) {

        mNavigateToDashboardCallback.checkCredentialValue(credentialValue);
    }
}
