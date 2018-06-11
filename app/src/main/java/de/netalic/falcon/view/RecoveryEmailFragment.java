package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.RecoveryEmailContract;

public class RecoveryEmailFragment extends Fragment implements RecoveryEmailContract.View {

    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_recoveryemail, container, false);
        setHasOptionsMenu(true);
        return mRoot;
    }

    public static RecoveryEmailFragment newInstance() {

        return new RecoveryEmailFragment();
    }

    @Override
    public void setPresenter(RecoveryEmailContract.Presenter presenter) {

    }
}
