package de.netalic.falcon.ui.authentication.authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AuthenticationPatternFragment extends Fragment implements AuthenticationContract.View {

   private AuthenticationContract.Presenter mAuthenticationPresenter;
   private NavigateToDashboardCallback mNavigateToDashboardCallback;
   private int mAuthenticationType;

    @Override
    public void showTypeOfAuthentication(int authenticationType) {

        mAuthenticationType=authenticationType;

    }


    interface NavigateToDashboardCallback {

       void navigationToDashboard(int authenticationType);
   }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setPresenter(AuthenticationContract.Presenter presenter) {

        mAuthenticationPresenter=presenter;

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
        if (context instanceof NavigateToDashboardCallback){

            mNavigateToDashboardCallback=(NavigateToDashboardCallback) context;
        }
        else {

            throw new ClassCastException(context.toString() + " must implement NavigateToDashboardCallback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigateToDashboardCallback=null;
    }
}
