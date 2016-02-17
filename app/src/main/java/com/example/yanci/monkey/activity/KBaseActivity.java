package com.example.yanci.monkey.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.yanci.core.AppAction;
import com.example.yanci.monkey.KApplication;

/**
 * Created by yanci on 16/2/16.
 */
public class KBaseActivity extends FragmentActivity {

    // 上下文实例
    public Context context;
    // 应用全局的实例
    public KApplication application;
    // 核心层的Action实例
    public AppAction appAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        application = (KApplication) this.getApplication();
        appAction = application.getAppAction();
    }
}
