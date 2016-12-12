package com.example.test_volley;

import android.app.Application;
import android.content.Context;

import java.io.File;

/**
 * Created by adapter on 2014/6/20.
 */
public class MainApplication extends Application {

    private static final String HEAD_PICTURE_PATH = "/cacheIcon";
    private static Context sContext;
    private static MainActivity mMainActivity;
    private static boolean isNeedCoinUpdate = false;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static synchronized Context getContext() {
        return sContext;
    }

    public static boolean isIsNeedCoinUpdate() {
        return isNeedCoinUpdate;
    }

    public static void setIsNeedCoinUpdate(boolean isNeedCoinUpdate) {
        MainApplication.isNeedCoinUpdate = isNeedCoinUpdate;
    }

    public static void setmMainActivity(MainActivity mMainActivity) {
        MainApplication.mMainActivity = mMainActivity;
    }

    public static MainActivity getmMainActivity() {
        return mMainActivity;
    }
}

