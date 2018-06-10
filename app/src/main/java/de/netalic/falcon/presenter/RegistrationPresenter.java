package de.netalic.falcon.presenter;

import android.support.annotation.NonNull;
import android.widget.Toast;

import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;

import de.netalic.falcon.model.User;
import de.netalic.falcon.network.ApiClient;
import de.netalic.falcon.network.ApiInterface;
import de.netalic.falcon.repository.Deal;
import de.netalic.falcon.repository.IRepository;
import de.netalic.falcon.repository.user.UserRepository;
import de.netalic.falcon.repository.user.UserSource;
import de.netalic.falcon.view.RegistrationFragment;
import nuesoft.helpdroid.validation.Validator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

public class RegistrationPresenter implements RegistrationContract.Presenter {

    @NonNull
    private final RegistrationContract.View mRegistrationView;
    private UserRepository mUserRepository;
    private ApiInterface mApiInterface;
    private User mResponse;
    private RegistrationFragment mRegistrationFragment=new RegistrationFragment();

    public RegistrationPresenter(@NonNull RegistrationContract.View registrationView) {

        mRegistrationView = checkNotNull(registrationView);
        mRegistrationView.setPresenter(this);
    }

    @Override
    public void claim(User user) {

//        UserRepository.getInstance().claim(user).promise().then(new DoneCallback<Deal<User, UserSource>>() {
//            @Override
//            public void onDone(Deal<User, UserSource> result) {
//
//            }
//        }).fail(new FailCallback<Throwable>() {
//            @Override
//            public void onFail(Throwable result) {
//
//            }
//        });

        mUserRepository=UserRepository.getInstance();
        mUserRepository.claim(user, new IRepository.CallRepository<User>() {
            @Override
            public void onDone(Deal<User> deal) {

                ApiClient.getService().claim(user.getUdid(),user.getPhone()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if (response.isSuccessful()){

                            mResponse=response.body();
                            //mRegistrationFragment.show(mResponse.getPhone());
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {


                    }
                });

            }
        });
    }

    @Override
    public void start() {

    }
}
