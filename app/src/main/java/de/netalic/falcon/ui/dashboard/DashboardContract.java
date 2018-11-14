package de.netalic.falcon.ui.dashboard;

import java.util.List;

import de.netalic.falcon.data.model.Wallet;
import de.netalic.falcon.ui.base.BasePresenter;
import de.netalic.falcon.ui.base.BaseView;

public interface DashboardContract {

    interface View extends BaseView<Presenter> {

        void setWalletList(List<Wallet> data);
    }

    interface Presenter extends BasePresenter {
        void getWalletList();
    }
}
