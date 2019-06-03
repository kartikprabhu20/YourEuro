package com.artexceptionals.youreuro;

import android.app.Application;
import android.content.Context;

public class YourEuroApp extends Application {

    private static YourEuroApp context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = (YourEuroApp) getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
