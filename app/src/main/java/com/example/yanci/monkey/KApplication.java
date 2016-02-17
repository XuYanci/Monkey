package com.example.yanci.monkey;

import android.app.Application;

import com.example.yanci.core.AppAction;
import com.example.yanci.core.AppActionImpl;

/**
 * Created by yanci on 16/2/16.
 */
public class KApplication extends Application{
    private AppAction appAction;

    @Override
    public void onCreate() {
        super.onCreate();
        appAction = new AppActionImpl(this);
    }

    public AppAction getAppAction() {
        return appAction;
    }
}
