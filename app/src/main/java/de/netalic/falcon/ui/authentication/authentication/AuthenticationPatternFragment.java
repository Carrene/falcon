package de.netalic.falcon.ui.authentication.authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;

import java.util.List;

import de.netalic.falcon.R;
import de.netalic.falcon.util.SnackbarUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class AuthenticationPatternFragment extends Fragment implements AuthenticationContract.View {

    private AuthenticationContract.Presenter mAuthenticationPresenter;
    private NavigateToDashboardCallback mNavigateToDashboardCallback;
    private View mRoot;
    private PatternLockView mPatternLockView;
    private int mAttemptTimeNumber;
    private String mCredentialValue;


    interface NavigateToDashboardCallback {

        void checkCredentialValue(String credentialValue);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationpattern, null);
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

    public static AuthenticationPatternFragment newInstance() {

        AuthenticationPatternFragment fragment = new AuthenticationPatternFragment();
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

        mPatternLockView = mRoot.findViewById(R.id.patternview_authentication_pattern);
        mPatternLockView.setEnabled(true);

    }

    private void initUiListener() {

        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {


            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                if (mAttemptTimeNumber == 0) {

                    if (pattern.size() < 4) {
                        checkNotNull(getContext());
                        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.authenticationdefinition_connectfordots), getContext());
                    } else {
                        mAttemptTimeNumber++;

                    }
                    mPatternLockView.clearPattern();
                    return;
                }

                checkNotNull(getContext());
                SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.authenticationdefinition_setsuccessful), getContext());

                mCredentialValue = mPatternLockView.getPattern().toString();
                mPatternLockView.clearPattern();
                navigateToDashboard(mCredentialValue);
            }

            @Override
            public void onCleared() {

            }
        });
    }

    private void navigateToDashboard(String credentialValue) {

        mNavigateToDashboardCallback.checkCredentialValue(credentialValue);
    }

    public void setErrorOnSnackBar(){

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot,getContext().getString(R.string.authenticationpassword_passworddoesnotmatch),getContext());
    }
}
