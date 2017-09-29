package com.example.cognitev.nearby;

import android.app.Application;
import android.content.Context;

import com.example.cognitev.nearby.base.BaseActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Salma on 9/21/2017.
 */
public class NearbyApplication extends Application{
    private static NearbyApplication instance;
    protected Context context;
    private static String appMode;
    private ApplicationComponent appComponent;
    static final String MODE = "MODE";
    static final String MODE_REAL_TIME = "0";
    static final String MODE_SINGLE_UPDATE = "1";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        context = getBaseContext();
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        appComponent.inject(this);
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }


    public static NearbyApplication get() {
        return instance;
    }


    public static String getAppMode() {
        if (appMode == null || appMode.isEmpty()){
            appMode = NearbyApplication.get().getAppComponent().getSharedPreferences().getString(MODE,MODE_REAL_TIME);
        }
        return appMode;
    }

    public static boolean isModeRealTime() {
        return getAppMode().equals(MODE_REAL_TIME);
    }

    public static void setAppMode(String appMode) {
        NearbyApplication.get().getAppComponent().getSharedPreferences().edit().putString(MODE,appMode).apply();
        NearbyApplication.appMode = appMode;
    }
}
