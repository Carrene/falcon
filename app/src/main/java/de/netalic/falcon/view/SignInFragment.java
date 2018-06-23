package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.SignInContract;

public class SignInFragment extends Fragment implements SignInContract.View {

    private View mRoot;
    private Button mSignIn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot=inflater.inflate(R.layout.fragment_singin,null);
        initUiComponents();
        initListener();
        return mRoot;
    }

    public static SignInFragment newInstance() {

        return new SignInFragment();
    }

    @Override
    public void setPresenter(SignInContract.Presenter presenter) {


        }

    private void initUiComponents(){

        mSignIn=mRoot.findViewById(R.id.button_singin_singin);
    }

    public void initListener(){

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),RegistrationActivity.class);
                startActivity(intent);

            }
        });
    }
}
