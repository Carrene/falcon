package de.netalic.falcon.ui.receive;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.rate.RateRepository;

public class ReceivePresenter implements ReceiveContract.Presenter {

    private ReceiveContract.View mReceiveView;

    public ReceivePresenter(ReceiveContract.View receiveView) {
        mReceiveView = receiveView;
        mReceiveView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void listExchangeRate() {

        mReceiveView.showProgressBar();

        RepositoryLocator.getInstance().getRepository(RateRepository.class).getAll(deal -> {

            if (deal.getThrowable() != null) {

                mReceiveView.dismissProgressBar();
                mReceiveView.internetConnectionError();
            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        mReceiveView.setRateList(deal.getResponse().body());
                        mReceiveView.dismissProgressBar();
                        break;
                    }

                }
            }


        });

    }

}
