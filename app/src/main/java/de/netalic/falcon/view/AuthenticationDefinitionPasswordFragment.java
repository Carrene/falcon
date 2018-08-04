package de.netalic.falcon.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import de.netalic.falcon.R;


public class AuthenticationDefinitionPasswordFragment extends Fragment {

    private EditText mEditTextPassCode;
    private EditText mEditTextConfirmCode;
    private TextInputLayout mTextInputLayoutConfirmCode;
    private View mRoot;
    private TextView mTextViewMinimumLength;
    private TextView mTextViewCapitalLetter;
    private TextView mTextViewDigit;
    private TextView mTextViewSpecialCharacter;

    private NavigateToDashboardCallback mNavigateToDashboardCallback;

    interface NavigateToDashboardCallback {
        void navigationToDashboardFromPassword(String credentialValue);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationdefinitionpassword, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        validationPassCode();
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

    @Override
    public void onDetach() {

        super.onDetach();
        mNavigateToDashboardCallback = null;
    }

    public void initUiComponents() {

        mEditTextPassCode = mRoot.findViewById(R.id.edittext_authentication_entercode);
        mEditTextConfirmCode = mRoot.findViewById(R.id.edittext_authenticationdefinition_confirmcode);
        mTextInputLayoutConfirmCode = mRoot.findViewById(R.id.textinputlayout_authenticationdefinition_confirmpasscode);
        mTextViewMinimumLength = mRoot.findViewById(R.id.textView_authenticationdefinition_length);
        mTextViewCapitalLetter = mRoot.findViewById(R.id.textView_authenticationdefinition_capital);
        mTextViewDigit = mRoot.findViewById(R.id.textView_authenticationdefinition_digit);
        mTextViewSpecialCharacter = mRoot.findViewById(R.id.textView_authenticationdefinition_special);
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
                    mTextViewCapitalLetter.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    ;
                    i++;
                } else {
                    mTextViewCapitalLetter.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    i--;
                }

                if (CustomValidator.hasDigit(password)) {
                    mTextViewDigit.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    i++;

                } else {
                    mTextViewDigit.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    i--;

                }

                if (CustomValidator.hasCustomCharacters(password)) {
                    mTextViewSpecialCharacter.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    i++;

                } else {
                    mTextViewSpecialCharacter.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
                    i--;

                }
                if (CustomValidator.hasMinimumLength(password)) {
                    mTextViewMinimumLength.setTextColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
                    i++;

                } else {
                    mTextViewMinimumLength.setTextColor(ContextCompat.getColor(getContext(), R.color.colorRed));
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

            String password = mEditTextPassCode.getText().toString();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!s.toString().matches("") && !password.equals(s.toString())) {

                    mTextInputLayoutConfirmCode.setError(getContext().getString(R.string.authenticationdefinition_notmatch));

                } else {

                    mTextInputLayoutConfirmCode.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                String password = mEditTextPassCode.getText().toString();
                if (s.toString().equals(password) && mEditTextConfirmCode.isEnabled()) {

                    mTextInputLayoutConfirmCode.setError(null);
                    navigateToDashboard(s.toString());
                    mEditTextPassCode.setText("");
                    mEditTextConfirmCode.setText("");
                }
            }
        });
    }

    private void navigateToDashboard(String credentialValue) {

        mNavigateToDashboardCallback.navigationToDashboardFromPassword(credentialValue);
    }

    public static class CustomValidator {

        public static boolean hasMinimumLength(String password) {


            return password.length() > 7;
        }

        public static boolean hasCustomCharacters(String password) {

            String customCharacters[] = {"@", "#", "$", "%", "+", "=", "_", "*", "?"};
            for (
                    String customCharacter : customCharacters)

            {
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
}