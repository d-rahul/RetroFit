package com.test.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.test.database.DatabaseHelper;
import com.test.utils.Foreground;
import com.test.wsutils.WSClient;
import com.test.wsutils.WSClientListener;

import retrofit2.Retrofit;


public class MyApplication extends MultiDexApplication {
    public static final String TAG = MyApplication.class.getSimpleName();

    public static WSClientListener wsClientListener;

    @Override
    public void onCreate() {
        super.onCreate();
        wsClientListener = WSClient.getRetroFitAPIListener();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
