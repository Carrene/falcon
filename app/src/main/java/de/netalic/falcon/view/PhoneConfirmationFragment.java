package de.netalic.falcon.view;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.PhoneConfirmationContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneConfirmationFragment extends Fragment implements PhoneConfirmationContract.View {

    private PhoneConfirmationContract.Presenter mPresenter;
    //TODO:1 Milad why this variable is in class scope not method scope
    private CountDownTimer mCountDownTimer;
    //TODO:2 Milad name this variable completely mTextViewTimer
    private TextView mTextTimer;
    private View mRoot;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_phoneconfirmation, container, false);
        setHasOptionsMenu(true);
        initUiComponents();
        setTimer();
        return mRoot;
    }

    public static PhoneConfirmationFragment newInstance() {

        return new PhoneConfirmationFragment();
    }

    @Override
    public void setPresenter(PhoneConfirmationContract.Presenter presenter) {

        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showActivationCodeError() {
        //TODO: Milad, display error at text input layout of activation code
    }

    private void changePhoneNumber() {
        //TODO: Milad Go to registration page when change phone number is called
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
            }
        }
        return true;
    }

    private void initUiComponents() {

        mTextTimer = mRoot.findViewById(R.id.textview_phoneconfirmation_timer);

    }

    //TODO:6 Milad call bind from repository when user click on tick icon, Get help from UserRepositoryTest
    private void bind() {

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

                mTextTimer.setText("Resend");

            }
        }.start();
    }
}
