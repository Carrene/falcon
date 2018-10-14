package de.netalic.falcon.ui.charge;

import android.os.Handler;

import de.netalic.falcon.data.repository.base.RepositoryLocator;
import de.netalic.falcon.data.repository.receipt.ReceiptRepository;
import de.netalic.falcon.data.repository.transaction.TransactionRepository;

import static com.google.common.base.Preconditions.checkNotNull;

public class ChargeConfirmationPresenter implements ChargeConfirmationContract.Presenter {

    private final ChargeConfirmationContract.View mChargeConfirmationView;
    private Runnable mRunnableRequest;
    private Runnable mRunnableTimer;
    private int mRequestInterval = 3000;
    private int mTotalTimeInterval = 15000;
    private int mFirstRequestDelay = 1000;

    public ChargeConfirmationPresenter(ChargeConfirmationContract.View chargeConfirmationView) {

        mChargeConfirmationView = checkNotNull(chargeConfirmationView);
        mChargeConfirmationView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void finalizeCharge(int transactionId, String braintreeNonce) {

        mChargeConfirmationView.showProgressBar();
        RepositoryLocator.getInstance().getRepository(TransactionRepository.class).finalizeCharge(transactionId, braintreeNonce, deal -> {


            if (deal.getThrowable() != null) {
                //TODO: (Milad) Display error
                //TODO:(Ehsan) what error?
                mChargeConfirmationView.dismissProgressBar();

            } else {

                switch (deal.getResponse().code()) {

                    case 200: {

                        Handler handler = new Handler();
                        mRunnableTimer = () -> {

                            handler.removeCallbacks(mRunnableTimer);
                            handler.removeCallbacks(mRunnableRequest);
                            mChargeConfirmationView.dismissProgressBar();
                            //TODO(Ehsan) what to do in this state?
                        };

                        mRunnableRequest = new Runnable() {

                            @Override
                            public void run() {

                                RepositoryLocator.getInstance().getRepository(ReceiptRepository.class).get(deal.getModel().getId()
                                        , deal1 -> {
                                            if (deal1.getThrowable() == null) {

                                                switch (deal1.getModel().getStatus()) {
                                                    case "in-progress": {
                                                        handler.postDelayed(this, mRequestInterval);
                                                        break;
                                                    }

                                                    case "succeed": {
                                                        handler.removeCallbacks(mRunnableRequest);
                                                        handler.removeCallbacks(mRunnableTimer);
                                                        mChargeConfirmationView.navigationToChargeCompleted(deal1.getModel());
                                                        break;
                                                    }

                                                    case "failed": {
                                                        handler.removeCallbacks(mRunnableRequest);
                                                        mChargeConfirmationView.navigationToChargeFailed(deal1.getModel());
                                                        break;
                                                    }
                                                }
                                            } else {

                                                mChargeConfirmationView.showErrorWhenFailed();
                                            }
                                        });
                            }
                        };
                        handler.postDelayed(mRunnableRequest, mFirstRequestDelay);
                        handler.postDelayed(mRunnableTimer, mTotalTimeInterval);
                        break;
                    }

                    case 400: {
                        mChargeConfirmationView.showErrorBraintreeNonceIsMissing();
                        break;
                    }

                    case 404: {
                        if (deal.getResponse().message().equals("Transaction Not Found")) {
                            mChargeConfirmationView.showErrorTransactionNotFound();
                        } else {
                            mChargeConfirmationView.showErrorInvalidTransactionId();
                        }
                        break;
                    }

                    case 604: {
                        mChargeConfirmationView.showErrorCannotFinalizeFailedTransaction();
                        break;
                    }

                    case 401: {
                        mChargeConfirmationView.showErrorFinalizeTransferAsAnAnonymous();
                        break;
                    }
                }
            }
            mChargeConfirmationView.dismissProgressBar();
        });
    }
}