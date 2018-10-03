package de.netalic.falcon.ui.setting.recoveryemail;

import de.netalic.falcon.data.model.User;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;

public class SettingRecoveryEmailPresenter implements SettingRecoveryEmailContract.Presenter {


    private SettingRecoveryEmailContract.View mSettingRecoveryEmailView;


    public SettingRecoveryEmailPresenter(SettingRecoveryEmailContract.View settingRecoveryEmailView) {
        mSettingRecoveryEmailView = settingRecoveryEmailView;
        mSettingRecoveryEmailView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void updateEmail(User user) {

        mSettingRecoveryEmailView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(UserRepository.class).setEmail(user, deal -> {

            if (deal.getThrowable() != null) {

                mSettingRecoveryEmailView.dismissProgressBar();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mSettingRecoveryEmailView.navigateToDashboard();
                        break;
                    }
                    case 712: {

                        mSettingRecoveryEmailView.showErrorInvalidEmail();
                        break;
                    }
                    case 718: {

                        mSettingRecoveryEmailView.showErrorEmailAlreadyExists();
                        break;
                    }
                }


            }
            mSettingRecoveryEmailView.dismissProgressBar();

        });
    }
}
