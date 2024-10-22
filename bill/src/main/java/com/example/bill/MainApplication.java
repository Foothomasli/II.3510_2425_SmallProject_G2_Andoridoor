package com.example.bill;

import android.app.Application;
import android.util.Log;
/**
 * @author LQ
 */
public class MainApplication extends Application {
    private final static String TAG = "MainApplication";
    private static MainApplication mApp;

    public static MainApplication getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        mApp = this;
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, "onTerminate");
        super.onTerminate();
    }


}
