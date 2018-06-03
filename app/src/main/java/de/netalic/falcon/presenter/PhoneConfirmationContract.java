package de.netalic.falcon.presenter;

import de.netalic.falcon.view.BaseView;

public interface PhoneConfirmationContract {

    interface View extends BaseView<PhoneConfirmationContract.Presenter> {

        void showActivationCodeError();

    }

    interface Presenter extends BasePresenter {

        void resendActivationCode();
    }
}
