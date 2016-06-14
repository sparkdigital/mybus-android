package com.mybus;

import android.app.Application;
import android.content.Context;

/**
 * @author Lucas Dimitroff <ldimitroff@devspark.com>
 */
public class MyBus extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        setContext(this);
    }

    private void setContext(Context c){
        this.mContext = c;
    }

    public static Context getContext(){
        return mContext;
    }
}
