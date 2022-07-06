package com.hisdu.ses;

import android.app.Application;
import android.content.Context;
import android.util.Base64;

import com.activeandroid.ActiveAndroid;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        MyApplication.context = getApplicationContext();
        ActiveAndroid.initialize(this);
        Fresco.initialize(getApplicationContext());
    }

    public static String getHeader(String userName, String password) {
        String credentials = userName + ":" + password;
        String credBase64 = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT).replace("\n", "");
        return credBase64;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
