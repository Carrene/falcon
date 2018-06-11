package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.AuthenticationDefinitionContract;

public class AuthenticationDefinitionFragment extends Fragment implements AuthenticationDefinitionContract.View {

    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationdefinition, container, false);
        setHasOptionsMenu(true);
        return mRoot;
    }

    public static AuthenticationDefinitionFragment newInstance() {

        return new AuthenticationDefinitionFragment();
    }

    @Override
    public void setPresenter(AuthenticationDefinitionContract.Presenter presenter) {

    }
}
