package de.netalic.falcon.ui.registration.authnticationdefinition;

import android.support.annotation.NonNull;

import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;

import static com.google.common.base.Preconditions.checkNotNull;

public class AuthenticationDefinitionPresenter implements AuthenticationDefinitionContract.Presenter {

    @NonNull
    private final AuthenticationDefinitionContract.View mAuthenticationDefinitionView;

    public AuthenticationDefinitionPresenter(@NonNull AuthenticationDefinitionContract.View authenticationDefinitionView) {

        mAuthenticationDefinitionView = checkNotNull(authenticationDefinitionView);
        mAuthenticationDefinitionView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void saveCredential(String credentialValue, int type) {

        Authentication authentication = new Authentication(credentialValue, type);
        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).insert(authentication, deal -> {

            if (deal.getThrowable() != null) {
                throw new RuntimeException("Authentication has not been saved!");
            }
            mAuthenticationDefinitionView.navigateToPhoneInput();
        });
    }
}
