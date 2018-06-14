package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;

import java.util.List;

import de.netalic.falcon.R;

public class AuthenticationDefinitionPatternTab extends Fragment {

    private View mRoot;
    private PatternLockView mPatternLockView;
    private int mAttemptTimeNumber;
    private String mFirstAttemptPattern;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.patterntab_authenticationdefinition, container, false);
        initUiComponents();
        initListeners();
        return mRoot;
    }

    public void initUiComponents() {

        mPatternLockView = mRoot.findViewById(R.id.patternview_authenticationdefinition_pattern);
        mPatternLockView.setEnabled(true);
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
                        Snackbar.make(mRoot, getContext().getString(R.string.authenticationdefinition_connectfordots), Snackbar.LENGTH_LONG).show();
                    } else {
                        mAttemptTimeNumber++;
                        Snackbar.make(mRoot, getContext().getString(R.string.authenticationdefinition_tryforsecondtime), Snackbar.LENGTH_LONG).show();
                    }
                    mPatternLockView.clearPattern();
                    return;
                }
                secondAttemptPattern = pattern.toString();
                boolean isPatternSame = mFirstAttemptPattern.equals(secondAttemptPattern);
                if (!isPatternSame) {
                    mPatternLockView.clearPattern();
                    mAttemptTimeNumber = 0;
                    Snackbar.make(mRoot, getContext().getString(R.string.authenticationdefinition_nomatch), Snackbar.LENGTH_LONG).show();
                    return;
                }
                Snackbar.make(mRoot, getContext().getString(R.string.authenticationdefinition_setsuccessful), Snackbar.LENGTH_LONG).show();
                String credentialValue = mPatternLockView.getPattern().toString();
                mPatternLockView.clearPattern();
            }

            @Override
            public void onCleared() {

            }
        });

    }

}