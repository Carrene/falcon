package de.netalic.falcon.ui.load;

import androidx.annotation.NonNull;

import static com.google.common.base.Preconditions.checkNotNull;

public class LoadFailedPresenter implements LoadFailedContract.Presenter {


    @NonNull
    private final LoadFailedContract.View mChargeFailedView;

    public LoadFailedPresenter(LoadFailedContract.View chargeFailedView) {

        mChargeFailedView =checkNotNull(chargeFailedView);
        mChargeFailedView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
