package de.netalic.falcon.ui.setting;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mSettingView;

    public SettingPresenter(SettingContract.View settingView) {
        mSettingView = settingView;
    }

    @Override
    public void start() {

    }
}
