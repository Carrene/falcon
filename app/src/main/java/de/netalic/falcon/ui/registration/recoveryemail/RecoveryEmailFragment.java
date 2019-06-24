package de.netalic.falcon.ui.registration.recoveryemail;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.dashboard.DashboardActivity;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;
import nuesoft.helpdroid.validation.Validator;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecoveryEmailFragment extends Fragment implements RecoveryEmailContract.View {

    private View mRoot;
    private RecoveryEmailContract.Presenter mPresenter;
    private EditText mEditTextRecoveryEmail;
    private TextInputLayout mTextInputLayoutRecoveryEmail;
    private TextView mTextViewSkip;
    private User mUser;
    private static final String ARGUMENT_USER = "USER";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_recoveryemail, container, false);
        checkNotNull(getArguments());
        mUser = getArguments().getParcelable(ARGUMENT_USER);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initUiComponents();
        initListeners();
    }

    public static RecoveryEmailFragment newInstance(User user) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_USER, user);
        RecoveryEmailFragment recoveryEmailFragment = new RecoveryEmailFragment();
        recoveryEmailFragment.setArguments(bundle);
        return recoveryEmailFragment;
    }

    public static RecoveryEmailFragment newInstance() {

        return new RecoveryEmailFragment();
    }

    @Override
    public void setPresenter(RecoveryEmailContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);

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

    public void set() {

        mPresenter.set(mUser);
    }

    public void initUiComponents() {

        mTextInputLayoutRecoveryEmail = mRoot.findViewById(R.id.textinputlayout_recoveryemail_enterrecoveryemail);
        mEditTextRecoveryEmail = mRoot.findViewById(R.id.edittext_recoveryemail_enterrecoveryemail);
        mTextViewSkip = mRoot.findViewById(R.id.textview_recoveryemail_skip);

    }

    @Override
    public void navigateToDashboard() {

        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showErrorInvalidEmail() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.recoveryemail_invalidemail), getContext());
    }

    @Override
    public void showErrorEmailAlreadyExists() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.recoveryemail_emailalreadyexists), getContext());
    }


    @Override
    public void showProgressBar() {

        checkNotNull(getContext());
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showMaterialDialog();
        }
    }

    @Override
    public void dismissProgressBar() {

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).dismissMaterialDialog();
        }

    }

    public void initListeners() {

        mTextViewSkip.setOnClickListener(v -> {

            navigateToDashboard();
        });

        mEditTextRecoveryEmail.setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_DOWN) {

                switch (keyCode) {

                    case KeyEvent.KEYCODE_ENTER:

                        checkEmailSyntax();

                        return true;
                    default:
                        break;
                }
            }

            return false;
        });
    }

    public void checkEmailSyntax() {

        if (Validator.isEmailValid(mEditTextRecoveryEmail.getText().toString())) {

            mUser.setEmail(mEditTextRecoveryEmail.getText().toString());
            mTextInputLayoutRecoveryEmail.setError(null);
            set();

        } else {

            mTextInputLayoutRecoveryEmail.setError(getContext().getString(R.string.recoveryemail_thisemaildoesnotexist));

        }
    }
}
