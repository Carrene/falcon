package de.netalic.falcon.presenter;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import de.netalic.falcon.R;
import de.netalic.falcon.model.User;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.network.ApiInterface;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;
import de.netalic.falcon.repository.user.UserRepository;
import de.netalic.falcon.repository.user.UserSource;
import de.netalic.falcon.view.PhoneConfirmationActivity;
import de.netalic.falcon.view.RegistrationFragment;
import nuesoft.helpdroid.validation.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationPresenter implements RegistrationContract.Presenter {

    @NonNull
    private final RegistrationContract.View mRegistrationView;
    private User mResponse;
    private IRepository.CallRepository mCallRepository;

    public RegistrationPresenter(@NonNull RegistrationContract.View registrationView) {

        mRegistrationView = checkNotNull(registrationView);
        mRegistrationView.setPresenter(this);
    }

    @Override
    public void claim(User user,String countryCode,String phoneNumber) {

        UserRepository.getInstance().claim(user,deal ->{

            if (deal.getResponse().code()==200){
                mRegistrationView.navigationToPhoneconfirmation(user);

            }
            if (deal.getThrowable()!=null){

                mRegistrationView.errorForNullmessage();
            }

                }

        );

    }

    @Override
    public void start() {

    }

}
