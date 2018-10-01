package de.netalic.falcon.ui.setting.authenticationdefinition;

import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface SettingAuthenticationDefinitionContract {

    interface View extends BaseView<Presenter>{

        void navigationToAuthentication();


    }

    interface Presenter extends BasePresenter{

        void changeCredential(String credentialValue, int type);


    }
}
