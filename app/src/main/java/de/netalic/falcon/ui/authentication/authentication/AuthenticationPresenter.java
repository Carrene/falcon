package de.netalic.falcon.ui.authentication.authentication;

import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.util.DigestUtil;
import nuesoft.helpdroid.util.Converter;

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

                    case Authentication.PATTERN_TYPE: {

                        mAuthenticationView.showPatternLayout();

                        break;
                    }

                    case Authentication.PASSWORD_TYPE: {

                        mAuthenticationView.showPasswordLayout();

                        break;
                    }

                }
            }


        });

    }

    public void checkCredentialValue(String credentialValue) {


        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).get(deal -> {
            if (deal.getThrowable() == null) {

                if (deal.getModel().getCredential().equals(Converter.bytesToHexString(DigestUtil.digestSha512(credentialValue)))) {
                    mAuthenticationView.navigationToDashboard();
                } else {
                    if (deal.getModel().getAuthenticationType() == 1) {
                        mAuthenticationView.showTextInputLayoutError();
                    } else {

                        mAuthenticationView.showPatternLockError();
                    }

                }
            }
        });
    }
}
