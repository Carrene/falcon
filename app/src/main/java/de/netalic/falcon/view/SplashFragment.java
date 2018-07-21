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
import de.netalic.falcon.presenter.SplashContract;

public class SplashFragment extends Fragment implements SplashContract.View {

    private View mRoot;
    private Button mButtonSignIn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_splash, null);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initListener();
    }

    public static SplashFragment newInstance() {

        return new SplashFragment();
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {


    }

    private void initUiComponents() {

        mButtonSignIn = mRoot.findViewById(R.id.button_singin_singin);
    }

    public void initListener() {

        mButtonSignIn.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), RegistrationActivity.class);
            startActivity(intent);

        });
    }
}
