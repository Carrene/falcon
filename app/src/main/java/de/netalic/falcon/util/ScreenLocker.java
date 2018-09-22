package de.netalic.falcon.util;


import android.os.Handler;
import android.os.Looper;

public class ScreenLocker {

    private Handler mHandler;
    private Runnable mRunnable;
    private int mTime = 10000;
    private static ScreenLocker mInstance;

    public interface LockScreenInterface {
        void lock();
    }

    private ScreenLocker() {

    }

    public static ScreenLocker getInstance() {

        if (mInstance == null) {
            mInstance = new ScreenLocker();
        }
        return mInstance;
    }

    public synchronized void start(LockScreenInterface lockScreenInterface) {


        this.mHandler = new Handler(Looper.getMainLooper());
        this.mRunnable = () -> {

            stop();
            lockScreenInterface.lock();
        };
        this.mHandler.postDelayed(this.mRunnable, (long) this.mTime);
    }

    private synchronized void start() {

        if (this.mHandler != null) {
            this.mHandler.postDelayed(this.mRunnable, (long) this.mTime);
        }
    }

    public synchronized void restart() {

        this.stop();
        this.start();
    }

    public synchronized void stop() {

        if (this.mHandler != null) {
            this.mHandler.removeCallbacksAndMessages(null);
        }
    }

    public synchronized boolean isRunning() {

        return (this.mHandler != null);
    }
}

