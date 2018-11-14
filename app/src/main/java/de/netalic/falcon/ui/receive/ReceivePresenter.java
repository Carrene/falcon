package de.netalic.falcon.ui.receive;

public class ReceivePresenter implements ReceiveContract.Presenter {

    private ReceiveContract.View mReceiveView;

    public ReceivePresenter(ReceiveContract.View receiveView) {
        mReceiveView = receiveView;
        mReceiveView.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
