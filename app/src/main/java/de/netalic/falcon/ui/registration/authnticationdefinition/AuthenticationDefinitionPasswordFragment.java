package de.netalic.falcon.ui.registration.authnticationdefinition;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import de.netalic.falcon.R;
import de.netalic.falcon.util.CustomPasswordValidator;


public class AuthenticationDefinitionPasswordFragment extends Fragment {

    private TextInputEditText mEditTextPassCode;
    private TextInputEditText mEditTextConfirmCode;
    private TextInputLayout mTextInputLayoutConfirmCode;
    private View mRoot;
    private TextInputLayout mTextInputLayoutPassword;
    private Spannable mSpannable;
    private SaveCredentialCallback mNavigateToDashboardCallback;

    public interface SaveCredentialCallback {
        void saveCredentialFromPassword(String credentialValue);
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
        mTextInputLayoutConfirmCode.setAlpha(0.2f);
        mEditTextConfirmCode.setEnabled(false);
        mEditTextPassCode.setTransformationMethod(new PasswordTransformationMethod());
        mEditTextConfirmCode.setTransformationMethod(new PasswordTransformationMethod());

    }

    public void initListener() {

        mEditTextPassCode.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                if (checkPasswordIsValid(s.toString())) {
                    mEditTextConfirmCode.setEnabled(true);
                    mTextInputLayoutConfirmCode.setError(" ");
                    mTextInputLayoutConfirmCode.setAlpha(1f);

                } else {
                    mEditTextConfirmCode.setEnabled(false);
                    mTextInputLayoutConfirmCode.setAlpha(0.2f);
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

                if (s != null && !s.toString().equals("") && checkConfirmPasswordIsValid(s.toString())) {
                    navigateToDashboard(s.toString());
                }
            }
        });
    }

    private boolean checkConfirmPasswordIsValid(String passwordConfirm) {

        return passwordConfirm.equals(mEditTextPassCode.getText().toString());
    }

    private boolean checkPasswordIsValid(String password) {


        int counter = 0;

        mSpannable = new SpannableString(getString(R.string.authenticationdefinition_helpertext));

        if (CustomPasswordValidator.hasMinimumLength(password)) {

            setSpan(0, 16);
            counter++;
        }
        if (CustomPasswordValidator.hasCapitalLetter(password)) {

            setSpan(17, 44);
            counter++;
        }
        if (CustomPasswordValidator.hasDigit(password)) {

            setSpan(45, 65);
            counter++;
        }
        if (CustomPasswordValidator.checkSpace(password)) {

            setSpan(66, 80);
            counter++;
        }
        mTextInputLayoutPassword.setHelperText(mSpannable);

        return counter == 4;

    }

    private void setSpan(int start, int end) {

        mSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.persianGreen)), start, end, 0);
        mSpannable.setSpan(new StyleSpan(Typeface.BOLD), start, end, 0);
    }

    private void navigateToDashboard(String credentialValue) {

        mNavigateToDashboardCallback.saveCredentialFromPassword(credentialValue);
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}


