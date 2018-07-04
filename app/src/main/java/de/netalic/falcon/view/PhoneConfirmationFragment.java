package de.netalic.falcon.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.concurrent.TimeUnit;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.presenter.PhoneConfirmationContract;
import de.netalic.falcon.util.ActivityUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneConfirmationFragment extends Fragment implements PhoneConfirmationContract.View {

    private static final String ARGUMENT_USER = "USER";
    private PhoneConfirmationContract.Presenter mPresenter;
    private User mUser;
    private TextView mTextViewPhone;
    private TextView mTextViewChangeNumber;
    private EditText mEditTextReceiveCode;
    private TextView mTextViewTimer;
    private View mRoot;
    private boolean mIsRunning = true;
    private ProgressDialog mProgressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_phoneconfirmation, container, false);
        mUser = getArguments().getParcelable(ARGUMENT_USER);
        mProgressDialog=new ProgressDialog(getActivity());
        setHasOptionsMenu(true);
        initUiComponents();
        setTimer();
        setUserPhoneNumber();
        initListeners();
        return mRoot;
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

    @Override
    public void showActivationCodeError(String error) {

        Snackbar.make(mRoot, error, Snackbar.LENGTH_LONG).show();

    }

    private void changePhoneNumber() {

        Intent intent = new Intent(getActivity(), RegistrationActivity.class);
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

                ActivityUtil.hideSoftKeyboard(getActivity());
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
        mTextViewChangeNumber = mRoot.findViewById(R.id.textview_phoneconfirmation_changenumber);
        mEditTextReceiveCode = mRoot.findViewById(R.id.edittext_phoneconfirmation_receivecode);
        mTextViewTimer = mRoot.findViewById(R.id.textview_phoneconfirmation_timer);
    }

    private void bind() {

        mPresenter.bind(mUser);
    }

    private void initListeners() {

        mTextViewTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIsRunning == false) {
                    setTimer();
                    mPresenter.resendActivationCode(mUser);

                }
            }
        });

        mTextViewChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePhoneNumber();
            }
        });

        mEditTextReceiveCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

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
            }
        });
    }

    private void setTimer() {

        CountDownTimer countDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long minuteTimer = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long secondTimer = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
                mTextViewTimer.setText(String.format("%02d:%02d ", minuteTimer, secondTimer));
            }

            @Override
            public void onFinish() {

                mIsRunning = false;
                if (getContext() != null) {
                    mTextViewTimer.setText(getContext().getString(R.string.phoneconfirmation_resend));
                }
            }
        }.start();
    }

    private void setUserPhoneNumber() {

        mTextViewPhone.setText(mUser.getPhone());
    }

    @Override
    public void navigateToRecoveryEmail(User user) {

        Intent intent = new Intent(getActivity(), RecoveryEmailActivity.class);
        intent.putExtra("USER", user);
        startActivity(intent);
    }

    @Override
    public void showResendCodeAgain() {

        Snackbar.make(mRoot, getContext().getString(R.string.phoneconfirmation_sentcodeagain), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {

        ActivityUtil.showProgressBar(mProgressDialog);
    }

    @Override
    public void disMissShowProgressBar() {

        ActivityUtil.disMissShowProgressBar(mProgressDialog);
    }
}

