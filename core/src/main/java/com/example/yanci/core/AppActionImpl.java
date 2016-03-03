package com.example.yanci.core;

import android.content.Context;
import android.os.AsyncTask;

import com.example.yanci.api.Api;
import com.example.yanci.api.ApiImpl;
import com.example.yanci.model.AccessTokenResp;
import com.example.yanci.model.PersonalDetailResp;

/**
 * Created by yanci on 16/2/17.
 */
public class AppActionImpl implements AppAction{
    private Api api;



    public AppActionImpl(Context context) {
        this.api = new ApiImpl();
    }

    public void getAccessToken(final String client_id, final String client_secret,final String code, final AppActionCallBackListener<AccessTokenResp>listener) {
        // 1.判断参数是否正确

        // 2.网络请求
        new AsyncTask<Void,Void,AccessTokenResp>() {
            @Override
            protected AccessTokenResp doInBackground(Void... params) {
                return api.getAccessToken(client_id,client_secret,code);
            }

            @Override
            protected void onPostExecute(AccessTokenResp accessTokenResp) {
               if (listener!=null && accessTokenResp != null) {
                   listener.onSuccess(accessTokenResp);
               }else {
                   listener.onFailure(AppError.STATUS_ERROR_TIMEOUT,AppError.MESSAGE_ERROR_TIMEOUT);
               }
            }
        }.execute();
    }


    public void getPersonalDetailByAccessToken(final String access_token, final AppActionCallBackListener<PersonalDetailResp>listener) {

        new AsyncTask<Void,Void,PersonalDetailResp>() {
            @Override
            protected PersonalDetailResp doInBackground(Void... params) {
                return api.getPersonalDetailByAccessToken(access_token);
            }
            @Override
            protected void onPostExecute(PersonalDetailResp personalDetailResp) {
               if (listener!=null && personalDetailResp !=null) {
                   listener.onSuccess(personalDetailResp);
               }
                else {
                   listener.onFailure(AppError.STATUS_ERROR_TIMEOUT,AppError.MESSAGE_ERROR_TIMEOUT);
               }
            }
        }.execute();
    }
}
