package de.netalic.falcon.ui.authentication.authentication;

import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;

public class AuthenticationPresenter implements AuthenticationContract.Presenter {

    private AuthenticationContract.View mAuthenticationView;


    public AuthenticationPresenter(AuthenticationContract.View authenticationView) {
        mAuthenticationView = authenticationView;
        mAuthenticationView.setPresenter(this);
    }

    @Override
    public void start() {

        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).get(deal -> {

            if (deal.getThrowable()!=null){


            }

            else {

                switch (deal.getModel().getAuthenticationType()){

                    case 0:{

                       mAuthenticationView.showTypeOfAuthentication(0);

                        break;
                    }

                    case 1:{

                    mAuthenticationView.showTypeOfAuthentication(1);

                        break;
                    }

                }
            }


        });

    }
}
