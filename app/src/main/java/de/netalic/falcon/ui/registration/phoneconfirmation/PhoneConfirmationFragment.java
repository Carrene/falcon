package de.netalic.falcon.ui.registration.phoneconfirmation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.TimeUnit;

import de.netalic.falcon.R;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.ui.base.BaseActivity;
import de.netalic.falcon.ui.registration.phoneinput.PhoneInputActivity;
import de.netalic.falcon.ui.registration.recoveryemail.RecoveryEmailActivity;
import de.netalic.falcon.util.SnackbarUtil;
import nuesoft.helpdroid.UI.Keyboard;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneConfirmationFragment extends Fragment implements PhoneConfirmationContract.View {

    private static final String ARGUMENT_USER = "USER";
    private PhoneConfirmationContract.Presenter mPresenter;
    private User mUser;
    private TextView mTextViewPhone;
    private TextInputEditText mEditTextReceiveCode;
    private Button mButtonChangeNumber;
    private Button mButtonTimer;
    private View mRoot;
    private boolean mIsRunning = true;
    private CountDownTimer mCountDownTimer;
    //Timer time in milliseconds
    public static int waitingTime = 120000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_phoneconfirmation, container, false);
        mUser = getArguments().getParcelable(ARGUMENT_USER);
        return mRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initUiComponents();
        setTimer();
        setUserPhoneNumber();
        initListeners();
    }

    public static PhoneConfirmationFragment newInstance(User user) {

        PhoneConfirmationFragment fragment = new PhoneConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_USER, user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setPresenter(PhoneConfirmationContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    private void changePhoneNumber() {

        Intent intent = new Intent(getActivity(), PhoneInputActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {

        super.onStart();
        mPresenter.start();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_phoneconfirmation_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_phoneconfirmation_done: {

                Keyboard.hideKeyboard(mRoot);
                if (mEditTextReceiveCode.getText().toString().isEmpty()) {

                    TextInputLayout textInputLayout = mRoot.findViewById(R.id.textinputlayout_phoneconfirmation_receivecode);
                    textInputLayout.setError(getContext().getString(R.string.phoneconfirmation_Pleasefillbox));

                } else {
                    mUser.setActivationCode(mEditTextReceiveCode.getText().toString());
                    bind();
                }
            }
        }
        return true;
    }

    private void initUiComponents() {

        mTextViewPhone = mRoot.findViewById(R.id.textview_phoneconfirmation_number);
        mEditTextReceiveCode = mRoot.findViewById(R.id.edittext_phoneconfirmation_receivecode);
        mButtonChangeNumber=mRoot.findViewById(R.id.button_verificaiton_changenumber);
        mButtonTimer=mRoot.findViewById(R.id.button_verification_timer);
    }

    private void bind() {

        mPresenter.bind(mUser);
    }

    private void initListeners() {

        mButtonTimer.setOnClickListener(v -> {

            if (!mIsRunning) {
                setTimer();
                mPresenter.resendActivationCode(mUser);

            }
        });

        mButtonChangeNumber.setOnClickListener(v -> changePhoneNumber());

        mEditTextReceiveCode.setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:
                        mUser.setActivationCode(mEditTextReceiveCode.getText().toString());
                        bind();
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });



        mEditTextReceiveCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().length() == 6) {

                    mUser.setActivationCode(mEditTextReceiveCode.getText().toString());
                    bind();
                }
            }
        });
    }

    private void setTimer() {

        mCountDownTimer = new CountDownTimer(waitingTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long minuteTimer = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long secondTimer = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                mButtonTimer.setText(String.format("%02d:%02d ", minuteTimer, secondTimer));
                mButtonTimer.setBackground(getResources().getDrawable(R.drawable.all_colorprimarydarkbackgroundwithcornerradius));
                mButtonTimer.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                mButtonTimer.setEnabled(false);

            }

            @Override
            public void onFinish() {

                mButtonTimer.setEnabled(true);
                mIsRunning = false;
                if (getContext() != null) {
                    mButtonTimer.setBackground(getResources().getDrawable(R.drawable.all_colorsecandarybackgroundwithcornerradius));
                    mButtonTimer.setTextColor(getResources().getColor(R.color.colorBlack));
                    mButtonTimer.setText(getContext().getString(R.string.phoneconfirmation_resend));
                }
            }
        }.start();
    }

    private void setUserPhoneNumber() {

        String phoneNumber = mUser.getPhone();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            phoneNumber = PhoneNumberUtils.formatNumber(mUser.getPhone(), "US");
        }
        mTextViewPhone.setText(phoneNumber);
    }

    @Override
    public void showErrorInvalidUdidOrPhone() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.phoneconfirmation_invalidudidorphone), getContext());

    }

    @Override
    public void showErrorInvalidActivationCode() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.phoneconfirmation_invalidactivationcode), getContext());
    }

    @Override
    public void showErrorInvalidDeviceName() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.phoneconfirmation_invaliddevicename), getContext());
    }

    @Override
    public void navigateToRecoveryEmail(User user) {

        Intent intent = new Intent(getActivity(), RecoveryEmailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(ARGUMENT_USER, user);
        startActivity(intent);
    }

    @Override
    public void showResendCodeAgain() {

        checkNotNull(getContext());
        SnackbarUtil.showSnackbar(mRoot, getContext().getString(R.string.phoneconfirmation_sentcodeagain), getContext());

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

    @Override
    public void onPause() {

        mCountDownTimer.cancel();
        super.onPause();
    }


}
