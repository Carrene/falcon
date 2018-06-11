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

import java.util.concurrent.TimeUnit;

import de.netalic.falcon.R;
import de.netalic.falcon.presenter.PhoneConfirmationContract;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneConfirmationFragment extends Fragment implements PhoneConfirmationContract.View {

    private PhoneConfirmationContract.Presenter mPresenter;
    private TextView mTextViewTimer;
    private View mRoot;
    private boolean isRunning = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_phoneconfirmation, container, false);
        setHasOptionsMenu(true);
        initUiComponents();
        setTimer();
        initListeners();
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

        mTextViewTimer = mRoot.findViewById(R.id.textview_phoneconfirmation_timer);

    }

    //TODO:6 Milad call bind from repository when user click on tick icon, Get help from UserRepositoryTest,call from presenter
    private void bind() {

    }

    private void initListeners() {

        mTextViewTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRunning == false) {
                    setTimer();
                }
            }
        });
    }

    private void setTimer() {


        CountDownTimer mCountDownTimer = new CountDownTimer(120000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                long minute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long second = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));

                mTextViewTimer.setText(String.format("%02d:%02d ", minute, second));
                isRunning = true;
            }

            @Override
            public void onFinish() {

                mTextViewTimer.setText("Resend");
                isRunning = false;

            }
        }.start();
    }
}

