package de.netalic.falcon.ui.registration.authnticationdefinition;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface AuthenticationDefinitionContract {

    interface View extends BaseView<AuthenticationDefinitionContract.Presenter> {
        void navigateToPhoneInput();
    }

    interface Presenter extends BasePresenter {
        void saveCredential(String credentialValue, int type);
    }
}

