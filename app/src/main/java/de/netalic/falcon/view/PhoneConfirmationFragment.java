package de.netalic.falcon.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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
import de.netalic.falcon.presenter.PhoneConfirmationContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneConfirmationFragment extends Fragment implements PhoneConfirmationContract.View {

    private PhoneConfirmationContract.Presenter mPresenter;
    //TODO:1 Milad why this variable is in class scope not method scope
    private CountDownTimer mCountDownTimer;
    //TODO:2 Milad name this variable completely mTextViewTimer
    private TextView mTextTimer;
    private View mRoot;
    private static User mUser;
    private TextView mTextViewPhone;
    private TextView mTextViewChangeNumber;
    private EditText mEditTextReceiveCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_phoneconfirmation, container, false);
        setHasOptionsMenu(true);
        initUiComponents();
        setTimer();
        setUserPhoneNumber();
        initListeners();
        return mRoot;
    }

    public static PhoneConfirmationFragment newInstance(User user) {

        mUser = user;
        return new PhoneConfirmationFragment();
    }

    @Override
    public void setPresenter(PhoneConfirmationContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showActivationCodeError(String error) {
        //TODO: Milad, display error at text input layout of activation code
        Snackbar.make(mRoot, error, Snackbar.LENGTH_LONG).show();

    }

    private void changePhoneNumber() {
        //TODO: Milad Go to registration page when change phone number is called
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
                //TODO: pass user to presenter based on input
//                mPresenter.register(user);

                if (mEditTextReceiveCode.getText().toString().isEmpty()) {

                    TextInputLayout textInputLayout = mRoot.findViewById(R.id.textinputlayout_phoneconfirmation_receivecode);
                    textInputLayout.setError(getContext().getString(R.string.phoneconfirmation_Pleasefillbox));

                } else {
                    bind();
                }
            }
        }
        return true;
    }

    private void initUiComponents() {

        mTextTimer = mRoot.findViewById(R.id.textview_phoneconfirmation_timer);
        mTextViewPhone = mRoot.findViewById(R.id.textview_phoneconfirmation_number);
        mTextViewChangeNumber = mRoot.findViewById(R.id.textview_phoneconfirmation_changenumber);
        mEditTextReceiveCode = mRoot.findViewById(R.id.edittext_phoneconfirmation_receivecode);

    }

    //TODO:6 Milad call bind from repository when user click on tick icon, Get help from UserRepositoryTest,call from presenter
    private void bind() {

        mPresenter.bind(mUser);

    }

    //TODO:3 Milad Restart counter when user click resend item

    private void setTimer() {

        mCountDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                //TODO:4 Milad Try to implement it better with string format for XX:XX. Implement it as clean as possible

                int minutes = (int) millisUntilFinished / 60000;
                int seconds = (int) millisUntilFinished % 60000 / 1000;
                String timeLeftText;
                timeLeftText = "0" + minutes;
                timeLeftText += ":";
                if (seconds < 10) {
                    timeLeftText += "0";
                }
                timeLeftText += "" + seconds;

                mTextTimer.setText(timeLeftText);
            }

            @Override
            public void onFinish() {

                mTextTimer.setText(getContext().getString(R.string.phoneconfirmation_resend));

            }
        }.start();
    }

    private void setUserPhoneNumber() {

        mTextViewPhone.setText(mUser.getPhone());
    }

    private void initListeners() {

        mTextViewChangeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changePhoneNumber();
            }
        });


    }
}
