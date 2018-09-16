package de.netalic.falcon.ui.authentication.authentication;

import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;

public class AuthenticationPresenter implements AuthenticationContract.Presenter {

    private AuthenticationActivity mAuthenticationView;


    public AuthenticationPresenter(AuthenticationActivity authenticationView) {
        mAuthenticationView = authenticationView;
        mAuthenticationView.setPresenter(this);
    }

    @Override
    public void start() {

        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).get(deal -> {

            if (deal.getThrowable() != null) {


            } else {

                switch (deal.getModel().getAuthenticationType()) {

                    case 0: {

                        mAuthenticationView.showPasswordLayout();

                        break;
                    }

                    case 1: {

                        mAuthenticationView.showPatternLayout();

                        break;
                    }

                }
            }


        });

    }

    public void checkCredentialValue(String credentialValue) {

        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).checkCredentialValue(credentialValue, deal -> {

            if (deal.getThrowable() != null) {


            } else {

                if (credentialValue.equals(deal.getModel().getCredential())) {

                    mAuthenticationView.navigationToDashboard();


                } else {

                    mAuthenticationView.showCredentialValueError();

                }


            }


        });


    }
}
