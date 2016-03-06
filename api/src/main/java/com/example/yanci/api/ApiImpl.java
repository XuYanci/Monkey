package com.example.yanci.api;

import com.example.yanci.api.net.BaseHttpHandler;
import com.example.yanci.model.AccessTokenResp;
import com.example.yanci.model.PersonalDetailResp;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yanci on 16/2/14.
 */
public class ApiImpl implements Api {

    private final static String TIME_OUT_EVENT = "0";
    private final static String TIME_OUT_EVENT_MSG = "连接服务器失败";

    private BaseHttpHandler httpHandler;

    public ApiImpl() { httpHandler = BaseHttpHandler.getInstance();}

    public AccessTokenResp getAccessToken(String client_id,String client_secret,String code) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("client_id",client_id);
        paramMap.put("client_secret",client_secret);
        paramMap.put("code",code);
        Type type = new TypeToken<AccessTokenResp>(){}.getType();
        try {
            return httpHandler.HttpPostRequest(SERVER_URL + SERVER_ACCESSTOKEN_SUFFIX,paramMap,type);
        }
        catch (IOException e) {
            return null;
        }
    }

    public PersonalDetailResp getPersonalDetailByAccessToken(String access_token) {
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("access_token",access_token);
        Type type = new TypeToken<PersonalDetailResp>(){}.getType();
        try {
            return httpHandler.HttpGetRequest(SERVER_API_URL + SERVER_USER_SUFFIX, paramMap, type);
        }
        catch (IOException e) {
            return null;
        }
    }


    public PersonalDetailResp getPersonalDetailByUserName(String username) {
        Type type = new TypeToken<PersonalDetailResp>(){}.getType();
        try {
            return  httpHandler.HttpGetRequest(SERVER_API_URL + SERVER_USERS_SUFFIX + "/" + username, null, type);
        }
        catch (IOException e) {
            return null;
        }
    }
}
