package de.netalic.falcon.ui.setting.authenticationdefinition;

import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;

public class SettingAuthenticationDefinitionPresenter implements SettingAuthenticationDefinitionContract.Presenter {

    private SettingAuthenticationDefinitionContract.View mSettingAuthenticationDefinitionView;

    public SettingAuthenticationDefinitionPresenter(SettingAuthenticationDefinitionContract.View settingAuthenticationDefinitionView) {

        mSettingAuthenticationDefinitionView = settingAuthenticationDefinitionView;
        mSettingAuthenticationDefinitionView.setPresenter(this);
    }

    @Override
    public void changeCredential(String credentialValue, int type) {

        Authentication authentication = new Authentication(credentialValue, type);
        RepositoryLocator.getInstance().getRepository(AuthenticationRepository.class).update(authentication, deal -> {

            if (deal.getThrowable() != null) {
                throw new RuntimeException("Authentication has not been saved!");
            }
            mSettingAuthenticationDefinitionView.navigationToSetting();
        });
    }

    @Override
    public void start() {

    }
}
