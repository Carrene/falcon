package de.netalic.falcon.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import de.netalic.falcon.R;


public class AuthenticationDefinitionPasswordTab extends Fragment {

    private EditText mEditTextPassCode;
    private EditText mEditTextConfirmCode;
    private TextInputLayout mTextInputLayoutConfirmCode;
    private TextInputLayout mTextInputLayoutPasswordCode;
    private View mRoot;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.passwordtab_authenticationdefinition, container, false);
        initUiComponents();
        validationPassCode();
        return mRoot;
    }

    public void initUiComponents() {

        mEditTextPassCode = mRoot.findViewById(R.id.edittext_authentication_entercode);
        mEditTextConfirmCode = mRoot.findViewById(R.id.edittext_authenticationdefinition_confirmcode);
        mEditTextConfirmCode.setEnabled(false);
        mTextInputLayoutConfirmCode = mRoot.findViewById(R.id.textinputlayout_authenticationdefinition_confirmpasscode);
        mTextInputLayoutPasswordCode = mRoot.findViewById(R.id.textinputlayout_authenticationdefinition_enterpasscode);

    }

    public void validationPassCode() {

        mEditTextPassCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (mEditTextPassCode.getText().toString().matches("")) {
                    mEditTextConfirmCode.setEnabled(false);
                    mEditTextConfirmCode.setText("");
                    mTextInputLayoutConfirmCode.setError(getContext().getString(R.string.authenticationdefinition_pleasefilloutboxabove));

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (CustomValidator.isPasswordValid(s.toString())) {

                    mEditTextConfirmCode.setEnabled(true);
                    mTextInputLayoutPasswordCode.setError(null);
                } else {

                    mEditTextConfirmCode.setText("");
                    mEditTextConfirmCode.setEnabled(false);
                    mTextInputLayoutPasswordCode.setError(getContext().getString(R.string.authenticationdefinition_Entervalidpassword));

                }

            }

        });

        mEditTextConfirmCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                String password = mEditTextPassCode.getText().toString();
                if (!s.toString().equals(password)) {

                    mTextInputLayoutConfirmCode.setError(getContext().getString(R.string.authenticationdefinition_notmatch));
                } else {
                    mTextInputLayoutConfirmCode.setError(null);

                }
            }

        });

    }

    public static class CustomValidator {

        public static boolean isPasswordValid(String password) {

            String passwordExpression = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%+=_*?])(?=\\S+$).{8,}$";
            return password.matches(passwordExpression);
        }

    }
}