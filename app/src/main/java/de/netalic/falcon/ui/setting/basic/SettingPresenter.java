package de.netalic.falcon.ui.setting.basic;

public class SettingPresenter implements SettingContract.Presenter {

    private SettingContract.View mSettingView;

    public SettingPresenter(SettingContract.View settingView) {
        mSettingView = settingView;
    }

    @Override
    public void start() {

    }
}
