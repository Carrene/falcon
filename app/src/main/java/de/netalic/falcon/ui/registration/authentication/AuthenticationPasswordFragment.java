package de.netalic.falcon.ui.registration.authentication;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import de.netalic.falcon.R;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class AuthenticationPasswordFragment extends Fragment implements AuthenticationContract.View {

    private AuthenticationContract.Presenter mAuthenticationPresenter;
    private NavigateToDashboardCallback mNavigateToDashboardCallback;
    private View mRoot;
    private EditText mEditTextPassword;
    private TextInputLayout mTextInputLayoutPassword;


    interface NavigateToDashboardCallback {

        void checkCredentialValue(String credentialValue);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationpassword, null);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initUiComponent();
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
            throw new ClassCastException(context.toString() + " must implement SaveCredentialCallback");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mNavigateToDashboardCallback = null;
    }

    private void initUiComponent() {

        mEditTextPassword = mRoot.findViewById(R.id.edittext_authentication_entercode);
        mTextInputLayoutPassword = mRoot.findViewById(R.id.textinputlayout_authentication_enterpasscode);
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
                    checkNotNull(getContext());
                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.everywhere_pleasefillbox), getContext());
                } else {
                    checkCredential(mEditTextPassword.getText().toString());
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkCredential(String credentialValue) {

        mNavigateToDashboardCallback.checkCredentialValue(credentialValue);
    }

    public void setErrorOnTextInputLayout() {

        checkNotNull(getContext());
        mTextInputLayoutPassword.setError(getContext().getString(R.string.authenticationpassword_incorrect));

    }
}
