package com.mybus;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class MyBus extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    private void setContext(Context c) {
        this.mContext = c;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);

        Realm.init(this);
    }
}
