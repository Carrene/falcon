package de.netalic.falcon.ui.registration.authnticationdefinition;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewtooltip.ViewTooltip;

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
    private View mPasswordCheckerView;
    private TextInputLayout mTextInputLayoutPassword;
    private ImageView mImageViewLength;
    private ImageView mImageViewCapital;
    private ImageView mImageViewDigit;
    private ImageView mImageViewSpecial;
    private ViewTooltip mViewTooltip;
    private ViewTooltip.TooltipView mTooltipView;

    private SaveCredentialCallback mNavigateToDashboardCallback;

    public interface SaveCredentialCallback {
        void saveCredentialFromPassword(String credentialValue);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_authenticationdefinitionpassword, container, false);
        mPasswordCheckerView = inflater.inflate(R.layout.passwordchecker_authenticationdefinitionpassword, null);

        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initUiComponents();
        initListener();

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof SaveCredentialCallback) {
            mNavigateToDashboardCallback = (SaveCredentialCallback) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement SaveCredentialCallback");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mNavigateToDashboardCallback = null;
    }

    public void initUiComponents() {

        mEditTextPassCode = mRoot.findViewById(R.id.edittext_authenticationdefinition_entercode);
        mEditTextConfirmCode = mRoot.findViewById(R.id.edittext_authenticationdefinition_confirmcode);
        mTextInputLayoutConfirmCode = mRoot.findViewById(R.id.textinputlayout_authenticationdefinition_confirmpasscode);
        mTextInputLayoutPassword = mRoot.findViewById(R.id.textinputlayout_authenticationdefinition_enterpasscode);
        mTextViewMinimumLength = mPasswordCheckerView.findViewById(R.id.textView_authenticationdefinition_length);
        mTextViewCapitalLetter = mPasswordCheckerView.findViewById(R.id.textView_authenticationdefinition_capital);
        mTextViewDigit = mPasswordCheckerView.findViewById(R.id.textView_authenticationdefinition_digit);
        mTextViewSpecialCharacter = mPasswordCheckerView.findViewById(R.id.textView_authenticationdefinition_special);
        mImageViewCapital = mPasswordCheckerView.findViewById(R.id.imageview_authenticationdefinitionpassword_capital);
        mImageViewDigit = mPasswordCheckerView.findViewById(R.id.imageview_authenticationdefinitionpassword_digit);
        mImageViewLength = mPasswordCheckerView.findViewById(R.id.imageview_authenticationdefinitionpassword_length);
        mImageViewSpecial = mPasswordCheckerView.findViewById(R.id.imageview_authenticationdefinitionpassword_special);

        mViewTooltip = ViewTooltip
                .on(this, mTextInputLayoutPassword)
                .autoHide(false, 10000)
                .corner(20)
                .position(ViewTooltip.Position.BOTTOM)
                .customView(mPasswordCheckerView)
                .color(ContextCompat.getColor(getContext(), R.color.primary))
                .textColor(R.color.white)
                .clickToHide(true);
    }

    public void initListener() {


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

                if (password.length() > 0 && mTooltipView == null) {
                    mTooltipView = mViewTooltip.show();
                }


                int i = 0;
                if (CustomValidator.hasCapitalLetter(password)) {

                    mImageViewCapital.setImageResource(R.drawable.successcheck);

                    i++;
                } else {
                    mTextViewCapitalLetter.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mImageViewCapital.setImageResource(R.drawable.failedcheck);
                    i--;
                }

                if (CustomValidator.hasDigit(password)) {
                    mImageViewDigit.setImageResource(R.drawable.successcheck);
                    i++;

                } else {
                    mTextViewDigit.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mImageViewDigit.setImageResource(R.drawable.failedcheck);
                    i--;

                }

                if (CustomValidator.hasCustomCharacters(password)) {
                    mImageViewSpecial.setImageResource(R.drawable.successcheck);
                    i++;

                } else {
                    mTextViewSpecialCharacter.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mImageViewSpecial.setImageResource(R.drawable.failedcheck);
                    i--;

                }
                if (CustomValidator.hasMinimumLength(password)) {
                    mImageViewLength.setImageResource(R.drawable.successcheck);
                    i++;

                } else {
                    mTextViewMinimumLength.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    mImageViewLength.setImageResource(R.drawable.failedcheck);
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

                if (s.toString().length() > 0) {
                    mTooltipView = null;
                    mViewTooltip.close();
                }
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

        mNavigateToDashboardCallback.saveCredentialFromPassword(credentialValue);
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

    @Override
    public void onPause() {

        super.onPause();
        mViewTooltip.close();
        mTooltipView = null;
    }
}


