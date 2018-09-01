package de.netalic.falcon.ui.authentication.recoveryemail;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.repository.user.UserRepository;
import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.RepositoryLocator;

import static com.google.common.base.Preconditions.checkNotNull;

public class RecoveryEmailPresenter implements RecoveryEmailContract.Presenter {


    @NonNull
    private final RecoveryEmailContract.View mRecoveryEmailView;

    public RecoveryEmailPresenter(@NonNull RecoveryEmailContract.View recoveryEmailView) {

        mRecoveryEmailView = checkNotNull(recoveryEmailView);
        mRecoveryEmailView.setPresenter(this);
    }

    @Override
    public void start() {


    }

    @Override
    public void set(User user) {

        mRecoveryEmailView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(UserRepository.class).setEmail(user, deal -> {


            if (deal.getThrowable() != null) {
                mRecoveryEmailView.dismissProgressBar();


            } else {


                switch (deal.getResponse().code()) {

                    case 200: {

                        mRecoveryEmailView.navigateToAuthenticationDefinitionActivity();
                        break;
                    }
                    case 712: {

                        mRecoveryEmailView.showErrorInvalidEmail();
                        break;
                    }
                    case 718: {

                        mRecoveryEmailView.showErrorEmailAlreadyExists();
                        break;
                    }
                }
                mRecoveryEmailView.dismissProgressBar();
            }

        });

    }
}