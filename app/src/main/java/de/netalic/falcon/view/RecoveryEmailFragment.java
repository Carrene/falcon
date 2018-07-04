package de.netalic.falcon.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.RecoveryEmailContract;
import de.netalic.falcon.util.ActivityUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecoveryEmailFragment extends Fragment implements RecoveryEmailContract.View {

    private View mRoot;
    private RecoveryEmailContract.Presenter mPresenter;
    private EditText mEditTextRecoveryEmail;
    private TextInputLayout mTextInputLayoutRecoveryEmail;
    private TextView mTextViewSkip;
    private User mUser;
    private static final String ARGUMENT_USER = "USER";
    private ProgressDialog mProgressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_recoveryemail, container, false);
        mUser = getArguments().getParcelable(ARGUMENT_USER);
        mProgressDialog=new ProgressDialog(getActivity());
        setHasOptionsMenu(true);
        initUiComponents();
        checkEmailSyntax();
        initListeners();
        return mRoot;
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

        inflater.inflate(R.menu.menu_recoveryemail_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_recoveryemail_done: {

                ActivityUtil.hideSoftKeyboard(getActivity());
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
    public void navigateToAuthenticationDefinitionActivity() {

        Intent intent = new Intent(getActivity(), AuthenticationDefinitionActivity.class);
        intent.putExtra(ARGUMENT_USER, mUser);
        startActivity(intent);
    }

    @Override
    public void showErrorSetEmail(int code) {

        Snackbar.make(mRoot, String.valueOf(code), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {

        ActivityUtil.showProgressBar(mProgressDialog);
    }

    @Override
    public void disMissShowProgressBar() {

        ActivityUtil.disMissShowProgressBar(mProgressDialog);

    }

    public void initListeners() {

        mTextViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AuthenticationDefinitionActivity.class);
                intent.putExtra(ARGUMENT_USER, mUser);
                startActivity(intent);
            }
        });

        mEditTextRecoveryEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

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
            }
        });
    }

    public void checkEmailSyntax() {

        String emailPattern = "[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789._-]+" +
                "@[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]+\\.+" +
                "[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]+";

        if (mEditTextRecoveryEmail.getText().toString().matches("")) {

            mTextInputLayoutRecoveryEmail.setError(getContext().getString(R.string.recoveryemail_pleaseenteremailaddress));

        } else if (!mEditTextRecoveryEmail.getText().toString().matches(emailPattern)) {

            mTextInputLayoutRecoveryEmail.setError(getContext().getString(R.string.recoveryemail_thisemaildoesnotexist));

        } else {
            mTextInputLayoutRecoveryEmail.setError(null);
            mUser.setEmail(mEditTextRecoveryEmail.getText().toString());
            set();

        }

    }

}
