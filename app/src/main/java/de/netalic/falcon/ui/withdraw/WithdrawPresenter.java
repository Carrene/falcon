package de.netalic.falcon.ui.withdraw;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.wallet.WalletRepository;

import static com.google.common.base.Preconditions.checkNotNull;
public class WithdrawPresenter implements WithdrawContract.Presenter {

    private final WithdrawContract.View mWithdrawView;



    public WithdrawPresenter(WithdrawContract.View withdrawView) {

        mWithdrawView = checkNotNull(withdrawView);
        mWithdrawView.setPresenter(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void getWalletList() {

        mWithdrawView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(WalletRepository.class).getAll(deal -> {

            if (deal.getThrowable()!=null){

                mWithdrawView.dismissProgressBar();
                mWithdrawView.internetConnectionError();

            } else {
                switch (deal.getResponse().code()){

                    case 200:{

                        mWithdrawView.setListWallet(deal.getResponse().body());
                        break;
                    }
                }

            }
            mWithdrawView.dismissProgressBar();
        });

    }
}
