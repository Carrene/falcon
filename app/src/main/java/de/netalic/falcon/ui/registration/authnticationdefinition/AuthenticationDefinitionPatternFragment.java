package de.netalic.falcon.ui.registration.authnticationdefinition;

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

public class AuthenticationDefinitionPatternFragment extends Fragment {

    private View mRoot;
    private PatternLockView mPatternLockView;
    private int mAttemptTimeNumber;
    private String mFirstAttemptPattern;
    private SaveCredentialCallback mSaveCredentialCallback;

    public interface SaveCredentialCallback {
        void saveCredentialFromPattern(String credentialValue);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationdefinitionpattern, container, false);

        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initListeners();
    }

    public void initUiComponents() {

        mPatternLockView = mRoot.findViewById(R.id.patternview_authenticationdefinition_pattern);
        mPatternLockView.setEnabled(true);

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof SaveCredentialCallback) {
            mSaveCredentialCallback = (SaveCredentialCallback) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement SaveCredentialCallback");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mSaveCredentialCallback = null;
    }

    public void initListeners() {

        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {


            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                String secondAttemptPattern;
                if (mAttemptTimeNumber == 0) {
                    mFirstAttemptPattern = pattern.toString();

                    if (pattern.size() < 4) {
                        checkNotNull(getContext());
                        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.authenticationdefinition_connectfordots), getContext());
                    } else {
                        mAttemptTimeNumber++;

                        checkNotNull(getContext());
                        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.authenticationdefinition_tryforsecondtime), getContext());

                    }
                    mPatternLockView.clearPattern();
                    return;
                }
                secondAttemptPattern = pattern.toString();
                boolean isPatternSame = mFirstAttemptPattern.equals(secondAttemptPattern);
                if (!isPatternSame) {
                    mPatternLockView.clearPattern();
                    mAttemptTimeNumber = 0;
                    checkNotNull(getContext());
                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.authenticationdefinition_notmatch), getContext());
                    return;
                } else {
                    checkNotNull(getContext());
                    SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.authenticationdefinition_setsuccessful), getContext());

                    String credentialValue = mPatternLockView.getPattern().toString();
                    mPatternLockView.clearPattern();
                    navigateToDashboard(credentialValue);
                }
            }

            @Override
            public void onCleared() {

            }
        });

    }

    private void navigateToDashboard(String credentialValue) {

        mSaveCredentialCallback.saveCredentialFromPattern(credentialValue);
    }
}