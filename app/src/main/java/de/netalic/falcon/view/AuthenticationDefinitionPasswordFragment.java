package de.netalic.falcon.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import de.netalic.falcon.R;


public class AuthenticationDefinitionPasswordFragment extends Fragment {

    private EditText mEditTextPassCode;
    private EditText mEditTextConfirmCode;
    private TextInputLayout mTextInputLayoutConfirmCode;
    private TextInputLayout mTextInputLayoutPasswordCode;
    private View mRoot;
    private CheckBox mCheckBoxCapital;
    private CheckBox mCheckBoxDigit;
    private CheckBox mCheckBoxCustomChar;
    private CheckBox mCheckBoxMinimumLength;
    private NavigateToDashboardCallback mNavigateToDashboardCallback;

    interface NavigateToDashboardCallback {
        void navigationToDashboardFromPassword(String credentialValue);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationdefinitionpassword, container, false);
        initUiComponents();
        validationPassCode();
        return mRoot;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof NavigateToDashboardCallback) {
            mNavigateToDashboardCallback = (NavigateToDashboardCallback) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement NavigateToDashboardCallback");
        }
    }

    public void initUiComponents() {

        mEditTextPassCode = mRoot.findViewById(R.id.edittext_authentication_entercode);
        mEditTextConfirmCode = mRoot.findViewById(R.id.edittext_authenticationdefinition_confirmcode);
        mEditTextConfirmCode.setEnabled(false);
        mTextInputLayoutConfirmCode = mRoot.findViewById(R.id.textinputlayout_authenticationdefinition_confirmpasscode);
        mTextInputLayoutPasswordCode = mRoot.findViewById(R.id.textinputlayout_authenticationdefinition_enterpasscode);

        mCheckBoxCapital = mRoot.findViewById(R.id.checkbox_authenticationdefinition_capital);
        mCheckBoxCustomChar = mRoot.findViewById(R.id.checkbox_authenticationdefinition_custom_char);
        mCheckBoxDigit = mRoot.findViewById(R.id.checkbox_authenticationdefinition_digit);
        mCheckBoxMinimumLength = mRoot.findViewById(R.id.checkbox_authenticationdefinition_length);

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

                String password = s.toString();
                int i = 0;
                if (CustomValidator.hasCapitalLetter(password)) {
                    mCheckBoxCapital.setChecked(true);
                    i++;
                } else {
                    mCheckBoxCapital.setChecked(false);
                    i--;
                }

                if (CustomValidator.hasDigit(password)) {
                    mCheckBoxDigit.setChecked(true);
                    i++;

                } else {
                    mCheckBoxDigit.setChecked(false);
                    i--;

                }

                if (CustomValidator.hasCustomCharacters(password)) {
                    mCheckBoxCustomChar.setChecked(true);
                    i++;

                } else {
                    mCheckBoxCustomChar.setChecked(false);
                    i--;

                }
                if (CustomValidator.hasMinimumLength(password)) {
                    mCheckBoxMinimumLength.setChecked(true);
                    i++;

                } else {
                    mCheckBoxMinimumLength.setChecked(false);
                    i--;

                }
                if (i == 4) {
                    mEditTextConfirmCode.setEnabled(true);
                } else {
                    mEditTextConfirmCode.setText("");
                    mEditTextConfirmCode.setEnabled(false);
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
                if (!s.toString().equals(password) && mEditTextConfirmCode.isEnabled()) {

                    mTextInputLayoutConfirmCode.setError(getContext().getString(R.string.authenticationdefinition_notmatch));
                } else if (!s.toString().equals("") && s.toString().equals(password)) {
                    mTextInputLayoutConfirmCode.setError(null);
                    navigationToDashboard(s.toString());
                    mEditTextPassCode.setText("");
                    mEditTextConfirmCode.setText("");

                }
            }

        });

    }

    public static class CustomValidator {

        public static boolean hasMinimumLength(String password) {

            return password.length() > 8;
        }

        public static boolean hasCustomCharacters(String password) {

            String customCharactes[] = {"@", "#", "$", "%", "+", "=", "_", "*", "?"};
            for (String customCharacter : customCharactes) {
                if (password.contains(customCharacter)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean hasCapitalLetter(String password) {

            return !password.equals(password.toLowerCase());
        }

        public static boolean hasDigit(String password) {

            return password.matches(".*\\d+.*");
        }
    }

    public void navigationToDashboard(String credentialValue) {

        mNavigateToDashboardCallback.navigationToDashboardFromPassword(credentialValue);
    }
}