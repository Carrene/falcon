package de.netalic.falcon.ui.load;

import static com.google.common.base.Preconditions.checkNotNull;
public class LoadCompletedPresenter implements LoadCompletedContract.Presenter {

    private final LoadCompletedContract.View mChargeCompletedView;

    public LoadCompletedPresenter(LoadCompletedContract.View chargeCompletedView) {
        mChargeCompletedView =checkNotNull(chargeCompletedView);
        mChargeCompletedView.setPresenter(this);
    }


    @Override
    public void start() {

    }
}
