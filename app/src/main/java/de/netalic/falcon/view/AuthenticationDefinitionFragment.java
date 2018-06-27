package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.AuthenticationDefinitionContract;
import info.hoang8f.android.segmented.SegmentedGroup;

public class AuthenticationDefinitionFragment extends Fragment implements AuthenticationDefinitionContract.View {

    private View mRoot;
    private SegmentedGroup mSegmentedGroup;
    private RadioButton mRadioButtonPassword;
    // TODO: (check Ehsan) Arash said this field must not be static
    private static User sUser;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationdefinition, container, false);
        initUiComponents();
        setDefaultFragment();
        initListeners();
        return mRoot;
    }

    // TODO: (check Ehsan) Arash said this factory method must change based on changing user field to non-static
    public static AuthenticationDefinitionFragment newInstance(User user) {

        sUser = user;

        return new AuthenticationDefinitionFragment();
    }

    @Override
    public void setPresenter(AuthenticationDefinitionContract.Presenter presenter) {

    }

    private void initUiComponents() {

        mSegmentedGroup = mRoot.findViewById(R.id.segmented_authenticationdefinition_segmentedgroup);
        mRadioButtonPassword = mRoot.findViewById(R.id.radiobutton_authenticationdefinition_password);
        setHasOptionsMenu(true);
    }

    public void initListeners() {

        mSegmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.radiobutton_authenticationdefinition_password:

                        changeContainerFragment(new AuthenticationDefinitionPasswordTab());
                        break;

                    case R.id.radiobutton_authenticationdefinition_pattern:

                        changeContainerFragment(new AuthenticationDefinitionPatternTab());
                        break;
                }
            }
        });
    }

    public void setDefaultFragment() {

        mRadioButtonPassword.setChecked(true);
        changeContainerFragment(new AuthenticationDefinitionPasswordTab());

    }

    public void changeContainerFragment(Fragment fragment) {


        // TODO: (check Ehsan) Arash said it is better to define bundle in factory method of target fragment
        Bundle bundle = new Bundle();
        // TODO: (check Ehsan) Arash said it is better to have bundle keys in target fragment as static parameters
        bundle.putParcelable("User", sUser);
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransactionPassword = getFragmentManager().beginTransaction();
        fragmentTransactionPassword.replace(R.id.framelayout_authenticationdefinition_container, fragment);
        fragmentTransactionPassword.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_authentication_toolbar, menu);
    }

}