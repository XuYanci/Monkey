package com.example.yanci.core;

import android.content.Context;

import com.example.yanci.api.Api;
import com.example.yanci.api.ApiImpl;

/**
 * Created by yanci on 16/2/17.
 */
public class AppActionImpl implements AppAction{
    private Api api;

    public AppActionImpl(Context context) {
        this.api = new ApiImpl();
    }

}
