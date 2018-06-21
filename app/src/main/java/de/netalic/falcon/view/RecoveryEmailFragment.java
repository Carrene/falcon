package de.netalic.falcon.view;

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

import static com.google.common.base.Preconditions.checkNotNull;

public class RecoveryEmailFragment extends Fragment implements RecoveryEmailContract.View {

    private View mRoot;
    private RecoveryEmailContract.Presenter mPresenter;
    private EditText mEditTextRecoveryEmail;
    private TextInputLayout mTextInputLayoutRecoveryEmail;
    private TextView mTextViewSkip;
    private static User sUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_recoveryemail, container, false);
        setHasOptionsMenu(true);
        initUiComponents();
        initListeners();
        checkEmailSyntax();
        return mRoot;
    }

    public static RecoveryEmailFragment newInstance(User user) {

        sUser=user;
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

                   checkEmailSyntax();
                   return true;
            }
            default:
                break;

        }
        return true;
    }

    public void set() {
        mPresenter.set(sUser);

    }

    public void initUiComponents() {

        mTextInputLayoutRecoveryEmail = mRoot.findViewById(R.id.textinputlayout_recoveryemail_enterrecoveryemail);
        mEditTextRecoveryEmail = mRoot.findViewById(R.id.edittext_recoveryemail_enterrecoveryemail);
        mTextViewSkip=mRoot.findViewById(R.id.textview_recoveryemail_skip);

    }

    @Override
    public void navigateToAuthenticationDefinitionActivity() {

        Intent intent=new Intent(getActivity(),AuthenticationDefinitionActivity.class);
        startActivity(intent);
    }

    @Override
    public void showErrorSetEmail(int code) {

        Snackbar.make(mRoot,String.valueOf(code),Snackbar.LENGTH_LONG).show();
    }

    public void initListeners(){

        mTextViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),AuthenticationDefinitionActivity.class);
                startActivity(intent);
            }
        });

        mEditTextRecoveryEmail.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction()==KeyEvent.ACTION_DOWN){

                    switch (keyCode){

                        case KeyEvent.KEYCODE_ENTER:

                            checkEmailSyntax();

                            return  true;
                         default:
                             break;
                    }
                }

                return false;
            }
        });
    }

    public void checkEmailSyntax(){

        String emailPattern = "[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789._-]+" +
                "@[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]+\\.+" +
                "[abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ]+";
        if (!mEditTextRecoveryEmail.getText().toString().matches(emailPattern)) {

            mTextInputLayoutRecoveryEmail.setError(getContext().getString(R.string.recoveryemail_thisemaildoesnotexist));

        } else {
            mTextInputLayoutRecoveryEmail.setError(null);
            set();

        }
        if (mEditTextRecoveryEmail.getText().toString().matches("")) {

            mTextInputLayoutRecoveryEmail.setError(getContext().getString(R.string.recoveryemail_pleaseenteremailaddress));

        }

    }
}
