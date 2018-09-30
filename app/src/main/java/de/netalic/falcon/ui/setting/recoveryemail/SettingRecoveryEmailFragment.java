package de.netalic.falcon.ui.setting.recoveryemail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.R;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;
import nuesoft.helpdroid.validation.Validator;

public class SettingRecoveryEmailFragment extends Fragment implements SettingRecoveryEmailContract.View {

    private View mRoot;
    private SettingRecoveryEmailContract.Presenter mSettingRecoveryEmailPresenter;
    private User mUser;
    private EditText mEditTextRecoveryEmail;
    private TextInputLayout mTextInputEditTextRecoveryEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_settingrecoveryemail, null);
        setHasOptionsMenu(true);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUiComponent();
        fetchUser();
    }

    @Override
    public void setPresenter(SettingRecoveryEmailContract.Presenter presenter) {

        mSettingRecoveryEmailPresenter = presenter;
    }

    @Override
    public void showProgressBar() {

        ((BaseActivity) getActivity()).showMaterialDialog();
    }

    @Override
    public void dismissProgressBar() {

        ((BaseActivity) getActivity()).dismissMaterialDialog();
    }

    public static SettingRecoveryEmailFragment newInstance() {

        SettingRecoveryEmailFragment fragment = new SettingRecoveryEmailFragment();
        return fragment;
    }

    @Override
    public void navigateToDashboard() {

        Intent intent = new Intent(getContext(), DashboardActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorInvalidEmail() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.recoveryemail_invalidemail), getContext());
    }

    @Override
    public void showErrorEmailAlreadyExists() {

        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.recoveryemail_emailalreadyexists), getContext());
    }

    private void updateEmail() {
        mSettingRecoveryEmailPresenter.updateEmail(mUser);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_everywhere_thathastick, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_everywhere_done: {

                Keyboard.hideKeyboard(mRoot);
                checkEmailSyntax();

                return true;
            }
            default:
                break;

        }
        return true;
    }

    public void checkEmailSyntax() {

        if (Validator.isEmailValid(mEditTextRecoveryEmail.getText().toString())) {

            mUser.setEmail(mEditTextRecoveryEmail.getText().toString());
            mTextInputEditTextRecoveryEmail.setError(null);
            updateEmail();

        } else {

            mTextInputEditTextRecoveryEmail.setError(getContext().getString(R.string.recoveryemail_thisemaildoesnotexist));

        }
    }

    private void initUiComponent() {

        mEditTextRecoveryEmail = mRoot.findViewById(R.id.edittext_settingrecoveryemail_enterrecoveryemail);
        mTextInputEditTextRecoveryEmail = mRoot.findViewById(R.id.textinputlayout_settingrecoveryemail_enterrecoveryemail);
    }

    private void fetchUser() {

        SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance());
        int id = (int) Parser.getTokenBody(sharedPreferencesJwtPersistor.get()).get("id");
        RepositoryLocator.getInstance().getRepository(UserRepository.class).get(id, deal -> {

            mUser = deal.getModel();
        });


    }
}
