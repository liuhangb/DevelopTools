package com.example.order.developtools;

import android.app.Application;

/**
 * Created by lh, 2020/12/14
 */
public class DemoApplication extends Application {
    private static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }
}
