package de.netalic.falcon.ui.setting.basic;

import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mSettingView;

    public SettingPresenter(SettingContract.View settingView) {
        mSettingView = settingView;
        mSettingView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loginMethod() {

        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).get(deal -> {
            if (deal.getThrowable() == null) {
                switch (deal.getModel().getAuthenticationType()) {
                    case Authentication.PATTERN_TYPE: {
                        //TODO(Milad) Use Authentication.type to view and view use the string file resource
                        mSettingView.setMethodType("pattern");
                        break;
                    }

                    case Authentication.PASSWORD_TYPE: {
                        mSettingView.setMethodType("password");
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void recoveryEmail(int id) {

        //TODO(Milad) Use token for getting email
        RepositoryLocator.getInstance().getRepository(UserRepository.class).get(id, deal -> {

            if (deal.getThrowable() == null) {

                if (deal.getModel().getIsActive()) {

                    mSettingView.setRecoveryEmailState(deal.getModel().getEmail());

                } else {
                    //TODO (Milad) Move view logic to view
                    mSettingView.setRecoveryEmailState("Email not set");
                }
            }

        });
    }
}
