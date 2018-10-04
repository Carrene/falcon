package de.netalic.falcon.ui.setting.basic;

import java.util.Map;

import de.netalic.falcon.MyApp;
import de.netalic.falcon.data.model.Authentication;
import de.netalic.falcon.data.repository.authentication.AuthenticationRepository;
import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.user.UserRepository;
import nuesoft.helpdroid.network.SharedPreferencesJwtPersistor;
import nuesoft.helpdroid.util.Parser;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mSettingView;
    SharedPreferencesJwtPersistor sharedPreferencesJwtPersistor = new SharedPreferencesJwtPersistor(MyApp.getInstance().getApplicationContext());
    Map<String, Object> tokenBody = Parser.getTokenBody(sharedPreferencesJwtPersistor.get());

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
                        mSettingView.setPatternType();
                        break;
                    }

                    case Authentication.PASSWORD_TYPE: {
                        mSettingView.setPasswordType();
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void recoveryEmail(String recoveryEmail) {


        String email = (String) tokenBody.get(recoveryEmail);

        if (email == null) {

            mSettingView.setEmailNotSet();
        } else {

            mSettingView.setRecoveryEmailState(email);
        }

    }

    @Override
    public void phoneNumber(String phoneNumber) {

        String phone = (String) tokenBody.get(phoneNumber);

        if (phone == null) {

            mSettingView.setPhoneNumberNotSet();
        } else {

            mSettingView.setPhoneNumber(phone);
        }
    }

    @Override
    public void baseCurrency() {

        RepositoryLocator.getInstance().getRepository(UserRepository.class).get((Integer) tokenBody.get("id"), deal -> {

            if (deal.getThrowable()==null){

                 mSettingView.setBaseCurrency(deal.getModel().getBaseCurrency());
            }

            else {

                mSettingView.setBaseCurrencyNotSet();
            }




        });

    }
}
