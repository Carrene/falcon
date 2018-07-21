package de.netalic.falcon.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import nuesoft.helpdroid.device.DeviceUtil;

public class CheckInternetConnectivity extends BroadcastReceiver {

    private NetworkStateChangeListener mNetworkStateChangeListener;

    public CheckInternetConnectivity(NetworkStateChangeListener networkStateChangeListener) {

        mNetworkStateChangeListener = networkStateChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent == null || intent.getExtras() == null)
            return;
        if (DeviceUtil.isConnectedToInternet(context)) {
            mNetworkStateChangeListener.networkAvailable();
            Log.d("connected", "connected");
        } else {
            mNetworkStateChangeListener.networkUnavailable();
            Log.d("connected", "disconnected");

        }

    }

    public void removeListener() {

        this.mNetworkStateChangeListener = null;
    }

    public interface NetworkStateChangeListener {
        void networkAvailable();

        void networkUnavailable();
    }
}
